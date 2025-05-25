package ac.za.cput.controller;
/*
 * DeliveryController.java
 * Controller for Delivery
 * Author: Anda Matomela
 * Date: 26 May 2025
 **/
import ac.za.cput.domain.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CandleSystem/delivery")
public class DeliveryController {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryController(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Delivery> create(@RequestBody Delivery delivery) {
        Delivery created = deliveryRepository.save(delivery);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{deliveryId}")
    public ResponseEntity<Delivery> read(@PathVariable String deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<Delivery> update(@RequestBody Delivery delivery) {
        if (!deliveryRepository.existsById(delivery.getDeliveryId())) {
            return ResponseEntity.notFound().build();
        }
        Delivery updated = deliveryRepository.save(delivery);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{deliveryId}")
    public ResponseEntity<Void> delete(@PathVariable String deliveryId) {
        if (!deliveryRepository.existsById(deliveryId)) {
            return ResponseEntity.notFound().build();
        }
        deliveryRepository.deleteById(deliveryId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Delivery>> getAll() {
        List<Delivery> all = (List<Delivery>) deliveryRepository.findAll();
        return ResponseEntity.ok(all);
    }
}