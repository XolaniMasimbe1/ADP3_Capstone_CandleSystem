package ac.za.cput.controller;

import ac.za.cput.domain.*;
import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.Order;
import ac.za.cput.factory.*;
import ac.za.cput.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private RetailStoreService retailStoreService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ManufactureService manufactureService;
    @Autowired
    private UserService userService;
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
    private User user;
    private Delivery delivery;
    private Invoice invoice;
    private Payment payment;
    private PaymentMethod paymentMethod;
    private Set<OrderItem> orderItems;

    private final String BASE_URL = "http://localhost:8080/CandleSystem";
    private final String ORDER_URL = BASE_URL + "/order";

    @BeforeAll
    void setUp() {
        try {
            // 1. Create and persist user
            user = userService.create(UserFactory.createUser("user", "password1234", UserRole.STORE));
            assertNotNull(user, "User creation failed");
            assertNotNull(user.getUserId(), "User ID is null");

            // 2. Create retail store
            retailStore = retailStoreService.create(RetailStoreFactory.createRetailStore(
                    "PicknPay",
                    "picknpay@gmail.com",
                    "0833133820",
                    "1685",
                    "Mathaba Street",
                    "Johannesburg",
                    "Gauteng",
                    "South Africa",
                    user
            ));
            assertNotNull(retailStore, "RetailStore creation failed");
            assertNotNull(retailStore.getStoreNumber(), "RetailStore ID is null");

            // 3. Create manufacture
            manufacture = manufactureService.create(ManufactureFactory.createManufacture("Candle Co."));
            assertNotNull(manufacture, "Manufacture creation failed");
            assertNotNull(manufacture.getManufacturerNumber(), "Manufacture ID is null");

            // 4. Create product
            product = productService.create(ProductFactory.createProduct(
                    "Rose Candle",
                    10.00,
                    100,
                    "Rose",
                    "Pink",
                    "Medium",
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
                ORDER_URL + "/create",
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
                ORDER_URL + "/read/" + order.getOrderNumber(),
                Order.class
        );

        assertNotNull(response.getBody(), "Response body is null");
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    void c_update() {
        Order updated = new Order.Builder()
                .copy(order)
                .setOrderStatus("Shipped")
                .build();

        restTemplate.put(
                ORDER_URL + "/update",
                updated
        );

        ResponseEntity<Order> response = restTemplate.getForEntity(
                ORDER_URL + "/read/" + order.getOrderNumber(),
                Order.class
        );

        assertNotNull(response.getBody(), "Response body is null");
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    void d_getAll() {
        ResponseEntity<Order[]> response = restTemplate.getForEntity(
                ORDER_URL + "/all",
                Order[].class
        );

        assertNotNull(response.getBody(), "Response body is null");
        assertTrue(response.getBody().length > 0, "No orders returned");
        System.out.println("All Orders:");
        for (Order o : response.getBody()) {
            System.out.println(o);
        }
    }
}