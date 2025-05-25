package ac.za.cput.controller;
/*
 * ManufactureController.java
 * Controller for Manufacture
 * Author: Anda Matomela
 * Date: 26 May 2025
 **/
import ac.za.cput.domain.Manufacture;
import ac.za.cput.repository.ManufactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CandleSystem/manufacture")
public class ManufactureController {

    private final ManufactureRepository manufactureRepository;

    @Autowired
    public ManufactureController(ManufactureRepository manufactureRepository) {
        this.manufactureRepository = manufactureRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Manufacture> create(@RequestBody Manufacture manufacture) {
        Manufacture created = manufactureRepository.save(manufacture);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{manufactureId}")
    public ResponseEntity<Manufacture> read(@PathVariable String manufactureId) {
        return manufactureRepository.findById(manufactureId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<Manufacture> update(@RequestBody Manufacture manufacture) {
        if (!manufactureRepository.existsById(manufacture.getManufactureId())) {
            return ResponseEntity.notFound().build();
        }
        Manufacture updated = manufactureRepository.save(manufacture);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{manufactureId}")
    public ResponseEntity<Void> delete(@PathVariable String manufactureId) {
        if (!manufactureRepository.existsById(manufactureId)) {
            return ResponseEntity.notFound().build();
        }
        manufactureRepository.deleteById(manufactureId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Manufacture>> getAll() {
        List<Manufacture> all = (List<Manufacture>) manufactureRepository.findAll();
        return ResponseEntity.ok(all);
    }
}