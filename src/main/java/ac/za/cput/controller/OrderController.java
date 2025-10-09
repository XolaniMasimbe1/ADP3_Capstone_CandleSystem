package ac.za.cput.controller;

import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;
import ac.za.cput.domain.Product;
import ac.za.cput.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
@Transactional
public class OrderController {
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @PostMapping("/create-with-email")
    public Order createWithEmailNotification(@RequestBody Order order) {
        return service.create(order);
    }

    @GetMapping("/read/{orderNumber}")
    public Order read(@PathVariable String orderNumber) {
        return service.read(orderNumber);
    }

    @PutMapping("/update")
    public Order update(@RequestBody Order order) {
        return service.update(order);
    }

    @GetMapping("/find/{orderNumber}")
    public Optional<Order> findByOrderNumber(@PathVariable String orderNumber) {
        return service.findByOrderNumber(orderNumber);
    }

    @GetMapping("/all")
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/store/{storeId}")
    public List<Order> getOrdersByStoreId(@PathVariable String storeId) {
        return service.getOrdersByStoreId(storeId);
    }

    @GetMapping("/with-images/{orderNumber}")
    public Order getOrderWithImages(@PathVariable String orderNumber) {
        return service.read(orderNumber);
    }

    @GetMapping("/test/{storeId}")
    public String testEndpoint(@PathVariable String storeId) {
        System.out.println("Test endpoint called with storeId: " + storeId);
        List<Order> orders = service.getOrdersByStoreId(storeId);
        System.out.println("Found " + orders.size() + " orders for storeId: " + storeId);
        return "Test successful - Found " + orders.size() + " orders for store: " + storeId;
    }

    @PostMapping("/test-email/{orderNumber}")
    public String testEmailNotification(@PathVariable String orderNumber) {
        try {
            Order order = service.read(orderNumber);
            if (order != null) {
                // This will trigger the email notification
                service.create(order);
                return "Email notification test successful for order: " + orderNumber;
            } else {
                return "Order not found: " + orderNumber;
            }
        } catch (Exception e) {
            return "Email test failed: " + e.getMessage();
        }
    }

    @GetMapping("/test/connectivity")
    public String testConnectivity() {
        return "Backend is running and accessible!";
    }

    @GetMapping("/test/product-images")
    public String testProductImages() {
        try {
            // Get all products and check if they have images
            List<Order> orders = service.getAll();
            StringBuilder result = new StringBuilder();
            result.append("Product Image Test Results:\n");
            
            for (Order order : orders) {
                if (order.getOrderItems() != null) {
                    for (OrderItem item : order.getOrderItems()) {
                        Product product = item.getProduct();
                        result.append("Product: ").append(product.getName())
                              .append(" - Image Data: ")
                              .append(product.getImageData() != null ? "Present (" + product.getImageData().length + " bytes)" : "NULL")
                              .append("\n");
                    }
                }
            }
            
            return result.toString();
        } catch (Exception e) {
            return "Error testing product images: " + e.getMessage();
        }
    }
}