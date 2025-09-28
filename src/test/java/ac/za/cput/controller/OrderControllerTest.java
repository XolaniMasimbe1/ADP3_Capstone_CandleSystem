package ac.za.cput.controller;

import ac.za.cput.domain.*;
import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.domain.Enum.BankName;
import ac.za.cput.domain.Order;
import ac.za.cput.factory.*;
import ac.za.cput.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RetailStoreService retailStoreService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ManufactureService manufactureService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMethodService paymentMethodService;

    private Order order;
    private RetailStore retailStore;
    private Product product;
    private Manufacture manufacture;
    private Delivery delivery;
    private Invoice invoice;
    private Payment payment;
    private PaymentMethod paymentMethod;
    private Set<OrderItem> orderItems;

    private String baseURL() {
        return "http://localhost:" + port + "/CandleSystem";
    }
    
    private String orderURL() {
        return baseURL() + "/order";
    }

    @BeforeAll
    void setUp() {
        try {
            // Generate unique data to avoid constraint violations
            String timestamp = String.valueOf(System.currentTimeMillis());
            
            // 1. Create retail store (RetailStore extends User, so no need for separate User)
            retailStore = retailStoreService.create(RetailStoreFactory.createRetailStore(
                    "PicknPay_" + timestamp,
                    "picknpay_" + timestamp + "@gmail.com",
                    "password123",
                    "123",
                    "Main Street",
                    "CBD",
                    "Cape Town",
                    Province.WESTERN_CAPE,
                    "8001",
                    "South Africa",
                    "John",
                    "Doe",
                    "john@picknpay.com",
                    "+27123456789",
                    "Jane",
                    "Smith",
                    "jane@picknpay.com",
                    "+27987654321"
            ));
            assertNotNull(retailStore, "RetailStore creation failed");
            assertNotNull(retailStore.getStoreNumber(), "RetailStore ID is null");
            assertNotNull(retailStore.getStoreId(), "Store ID is null");

            // 3. Create manufacture
            manufacture = manufactureService.create(ManufactureFactory.createManufacture("Candle Co."));
            assertNotNull(manufacture, "Manufacture creation failed");
            assertNotNull(manufacture.getManufacturerNumber(), "Manufacture ID is null");

            // 4. Create product
            byte[] productImageData = "rose-candle-image-data".getBytes();
            product = productService.create(ProductFactory.createProduct(
                    "Rose Candle",
                    10.00,
                    100,
                    "Rose",
                    "Pink",
                    "Medium",
                    productImageData,
                    manufacture
            ));
            assertNotNull(product, "Product creation failed");
            assertNotNull(product.getProductNumber(), "Product ID is null");

            // 5. Create order items
            orderItems = new HashSet<>();
            OrderItem item1 = OrderItemFactory.createOrderItem(2, product);
            OrderItem item2 = OrderItemFactory.createOrderItem(3, product);
            assertNotNull(item1, "First OrderItem creation failed");
            assertNotNull(item2, "Second OrderItem creation failed");
            orderItems.add(item1);
            orderItems.add(item2);

            // 6. Create delivery
            delivery = deliveryService.create(DeliveryFactory.createDelivery("Pending"));
            assertNotNull(delivery, "Delivery creation failed");
            assertNotNull(delivery.getDeliveryNumber(), "Delivery ID is null");

            // 7. Create invoice
            invoice = invoiceService.create(InvoiceFactory.createInvoice(50.00));
            assertNotNull(invoice, "Invoice creation failed");
            assertNotNull(invoice.getInvoiceNumber(), "Invoice ID is null");

            // 8. Create payment method
            paymentMethod = paymentMethodService.create(
                    PaymentMethodFactory.createPaymentMethod(
                            PaymentType.CREDIT_CARD,
                            LocalDateTime.now()
                    )
            );
            assertNotNull(paymentMethod, "PaymentMethod creation failed");
            assertNotNull(paymentMethod.getPaymentMethodId(), "PaymentMethod ID is null");

            // 9. Create payment
            payment = paymentService.create(PaymentFactory.createPayment(50.00, paymentMethod));
            assertNotNull(payment, "Payment creation failed");
            assertNotNull(payment.getPaymentNumber(), "Payment ID is null");

            // 10. Create order
            order = OrderFactory.createOrder(
                    "Pending",
                    retailStore,
                    orderItems,
                    delivery,
                    invoice,
                    payment
            );
            assertNotNull(order, "Order creation failed");
            assertNotNull(order.getOrderNumber(), "Order number is null");

        } catch (Exception e) {
            System.err.println("Setup failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void a_create() {
        order.getOrderItems().forEach(item -> item.setOrder(order));

        ResponseEntity<Order> response = restTemplate.postForEntity(
                orderURL() + "/create",
                order,
                Order.class
        );

        assertNotNull(response.getBody(), "Response body is null");
        assertNotNull(response.getBody().getOrderNumber(), "Order number in response is null");
        order = response.getBody(); // Update the instance variable for subsequent tests
        System.out.println("Created: " + order);
    }
    @Test
    @org.junit.jupiter.api.Order(2)
    void b_read() {
        ResponseEntity<Order> response = restTemplate.getForEntity(
                orderURL() + "/read/" + order.getOrderNumber(),
                Order.class
        );

        assertNotNull(response.getBody(), "Response body is null");
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void c_update() {
        // Update the SAME order that was created in the first test
        assertNotNull(order, "Order should not be null - run create test first");
        
        Order updated = new Order.Builder()
                .copy(order)
                .setOrderStatus("Shipped")
                .build();

        restTemplate.put(
                orderURL() + "/update",
                updated
        );

        // Update our reference to the updated order
        order = updated;

        ResponseEntity<Order> response = restTemplate.getForEntity(
                orderURL() + "/read/" + order.getOrderNumber(),
                Order.class
        );

        assertNotNull(response.getBody(), "Response body is null");
        assertEquals("Shipped", response.getBody().getOrderStatus(), "Order status should be updated to Shipped");
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void d_getAll() {
        // Get all orders - should include the one we created and updated
        assertNotNull(order, "Order should not be null - run create test first");
        
        ResponseEntity<Order[]> response = restTemplate.getForEntity(
                orderURL() + "/all",
                Order[].class
        );

        assertNotNull(response.getBody(), "Response body is null");
        assertTrue(response.getBody().length > 0, "No orders returned");
        
        // Verify our created order is in the list
        boolean foundOurOrder = false;
        for (Order o : response.getBody()) {
            if (o.getOrderNumber().equals(order.getOrderNumber())) {
                foundOurOrder = true;
                break;
            }
        }
        assertTrue(foundOurOrder, "Our created order should be in the getAll results");
        
        System.out.println("All Orders (" + response.getBody().length + " total):");
        for (Order o : response.getBody()) {
            System.out.println(o);
        }
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    void e_testProductWithImage() {
        // Test that products in orders have image data
        assertNotNull(product, "Product should not be null");
        
        // Test getting product image
        ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                baseURL() + "/product/image/" + product.getProductNumber(),
                byte[].class
        );
        
        assertNotNull(imageResponse.getBody(), "Image should be retrievable");
        assertTrue(imageResponse.getBody().length > 0, "Image should have content");
        
        System.out.println("Product image retrieved successfully, size: " + imageResponse.getBody().length + " bytes");
        System.out.println("Product with image: " + product.getName() + " - Image available via API");
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    void f_createOrderWithCardPayment() {
        try {
            // Create card payment method
            PaymentMethod cardPaymentMethod = PaymentMethodFactory.createPaymentMethod(
                    PaymentType.CREDIT_CARD, 
                    LocalDateTime.now()
            );
            cardPaymentMethod = paymentMethodService.create(cardPaymentMethod);
            assertNotNull(cardPaymentMethod, "Card PaymentMethod creation failed");

            // Create card payment with card details
            Payment cardPayment = PaymentFactory.createCardPayment(
                    75.0, 
                    cardPaymentMethod, 
                    "9876543210987654", 
                    "Jane Smith", 
                    "06/26", 
                    "456", 
                    BankName.FNB
            );
            cardPayment = paymentService.create(cardPayment);
            assertNotNull(cardPayment, "Card Payment creation failed");
            assertNotNull(cardPayment.getCardNumber(), "Card number should not be null");
            assertEquals("9876543210987654", cardPayment.getCardNumber());
            assertEquals("Jane Smith", cardPayment.getNameOnCard());
            assertEquals("06/26", cardPayment.getExpiryDate());
            assertEquals("456", cardPayment.getCvv());
            assertEquals(BankName.FNB, cardPayment.getBank());

            // Create new order items for card payment order
            Set<OrderItem> cardOrderItems = new HashSet<>();
            OrderItem cardItem1 = OrderItemFactory.createOrderItem(1, product);
            OrderItem cardItem2 = OrderItemFactory.createOrderItem(2, product);
            cardOrderItems.add(cardItem1);
            cardOrderItems.add(cardItem2);

            // Create delivery for card payment order
            Delivery cardDelivery = deliveryService.create(DeliveryFactory.createDelivery("Pending"));
            assertNotNull(cardDelivery, "Card Delivery creation failed");

            // Create invoice for card payment order
            Invoice cardInvoice = invoiceService.create(InvoiceFactory.createInvoice(75.0));
            assertNotNull(cardInvoice, "Card Invoice creation failed");

            // Create order with card payment
            Order cardOrder = OrderFactory.createOrder(
                    "Pending",
                    retailStore,
                    cardOrderItems,
                    cardDelivery,
                    cardInvoice,
                    cardPayment
            );
            assertNotNull(cardOrder, "Card Order creation failed");

            // Set order reference on order items
            cardOrder.getOrderItems().forEach(item -> item.setOrder(cardOrder));

            // Create order via API
            ResponseEntity<Order> response = restTemplate.postForEntity(
                    orderURL() + "/create",
                    cardOrder,
                    Order.class
            );

            assertNotNull(response.getBody(), "Response body is null");
            assertNotNull(response.getBody().getOrderNumber(), "Order number in response is null");
            assertNotNull(response.getBody().getPayment(), "Payment should not be null");
            assertNotNull(response.getBody().getPayment().getCardNumber(), "Card number should not be null");
            assertEquals("9876543210987654", response.getBody().getPayment().getCardNumber());
            assertEquals("Jane Smith", response.getBody().getPayment().getNameOnCard());
            assertEquals("06/26", response.getBody().getPayment().getExpiryDate());
            assertEquals("456", response.getBody().getPayment().getCvv());
            assertEquals(BankName.FNB, response.getBody().getPayment().getBank());

            System.out.println("Created Order with Card Payment: " + response.getBody());
            System.out.println("Card Payment Details: " + response.getBody().getPayment());

        } catch (Exception e) {
            System.err.println("Card payment order creation failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}