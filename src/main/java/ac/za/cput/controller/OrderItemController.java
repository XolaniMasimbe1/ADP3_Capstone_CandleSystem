package ac.za.cput.controller;

import ac.za.cput.domain.OrderItem;
import ac.za.cput.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/create")
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.create(orderItem);
    }

    @GetMapping("/read/{id}")
    public OrderItem readOrderItem(@PathVariable Long id) {
        return orderItemService.read(id);
    }

    @PutMapping("/update")
    public OrderItem updateOrderItem(@RequestBody OrderItem orderItem) {
        return orderItemService.update(orderItem);
    }


    @GetMapping("/all")
    public List<OrderItem> getAllOrderItems() {
        return orderItemService.getAll();
    }
}