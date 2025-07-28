package ac.za.cput.controller;

import ac.za.cput.domain.Order;
import ac.za.cput.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final IOrderService service;

    @Autowired
    public OrderController(IOrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @GetMapping("/read/{orderNumber}")
    public Order read(@PathVariable int orderNumber) {
        return service.read(orderNumber);
    }

    @PutMapping("/update")
    public Order update(@RequestBody Order order) {
        return service.update(order);
    }

    @GetMapping("/find/{orderNumber}")
    public Order findById(@PathVariable int orderNumber) {
        return service.read(orderNumber);
    }

    @GetMapping("/all")
    public List<Order> getAll() {
        return service.getAll();
    }
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/