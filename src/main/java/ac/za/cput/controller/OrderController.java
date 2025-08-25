package ac.za.cput.controller;

import ac.za.cput.domain.Order;
import ac.za.cput.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
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

    @GetMapping("/store/{storeNumber}")
    public List<Order> getOrdersByStoreNumber(@PathVariable String storeNumber) {
        return service.getOrdersByStoreNumber(storeNumber);
    }
}