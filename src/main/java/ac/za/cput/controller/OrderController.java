package ac.za.cput.controller;

import ac.za.cput.domain.Order;
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
}