package ac.za.cput.controller;

import ac.za.cput.domain.Delivery;
import ac.za.cput.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> create(@RequestBody Delivery delivery) {
        try {
            System.out.println("Creating delivery with status: " + delivery.getStatus());
            Delivery createdDelivery = service.create(delivery);
            System.out.println("Delivery created successfully: " + createdDelivery.getDeliveryNumber());
            return ResponseEntity.ok(createdDelivery);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Delivery creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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