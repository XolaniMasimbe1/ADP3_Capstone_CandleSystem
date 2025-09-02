package ac.za.cput.controller;

import ac.za.cput.domain.Delivery;
import ac.za.cput.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/delivery")
@Transactional
public class DeliveryController {
    private final DeliveryService service;

    @Autowired
    public DeliveryController(DeliveryService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Delivery create(@RequestBody Delivery delivery) {
        return service.create(delivery);
    }

    @GetMapping("/read/{deliveryNumber}")
    public Delivery read(@PathVariable String deliveryNumber) {
        return service.read(deliveryNumber);
    }

    @PutMapping("/update")
    public Delivery update(@RequestBody Delivery delivery) {
        return service.update(delivery);
    }

    @GetMapping("/all")
    public List<Delivery> getAll() {
        return service.getAll();
    }
}