package ac.za.cput.controller;

import ac.za.cput.domain.OrderItem;
import ac.za.cput.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {

    private final IOrderItemService service;

    @Autowired
    public OrderItemController(IOrderItemService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public OrderItem create(@RequestBody OrderItem orderItem) {
        return service.create(orderItem);
    }

    @GetMapping("/read/{id}")
    public OrderItem read(@PathVariable Long id) {
        return service.read(id);
    }

    @PutMapping("/update")
    public OrderItem update(@RequestBody OrderItem orderItem) {
        return service.update(orderItem);
    }

    @GetMapping("/find/{id}")
    public OrderItem findById(@PathVariable Long id) {
        return service.read(id);
    }

    @GetMapping("/all")
    public List<OrderItem> getAll() {
        return service.getAll();
    }
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/