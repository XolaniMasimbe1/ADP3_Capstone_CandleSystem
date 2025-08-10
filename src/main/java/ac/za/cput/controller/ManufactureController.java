package za.ac.cput.controller;

/*
 * ManufactureController.java
 * Controller for Manufacture
 * Author: Anda Matomela
 * Student Number: 222578912
 * Date: 31 July 2025
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Manufacture;
import za.ac.cput.service.ManufactureService;

import java.util.List;

@RestController
@RequestMapping("/api/manufacture")
public class ManufactureController {

    @Autowired
    private ManufactureService manufactureService;

    @PostMapping("/create")
    public ResponseEntity<Manufacture> create(@RequestBody Manufacture manufacture) {
        return ResponseEntity.ok(manufactureService.create(manufacture));
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Manufacture> read(@PathVariable int id) {
        return ResponseEntity.ok(manufactureService.read(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Manufacture> update(@RequestBody Manufacture manufacture) {
        return ResponseEntity.ok(manufactureService.update(manufacture));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        return ResponseEntity.ok(manufactureService.delete(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Manufacture>> getAll() {
        return ResponseEntity.ok(manufactureService.getAll());
    }
}
