package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.service.RetailStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/store")
public class RetailStoreController {
    private final RetailStoreService service;

    @Autowired
    public RetailStoreController(RetailStoreService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<RetailStore> create(@RequestBody RetailStore retailStore) {
        RetailStore created = service.create(retailStore);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/read/{storeNumber}")
    public ResponseEntity<RetailStore> read(@PathVariable String storeNumber) {
        RetailStore retailStore = service.read(storeNumber);
        if (retailStore != null) {
            return ResponseEntity.ok(retailStore);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update") // FIX 1: Use @PutMapping for the update method.
    public ResponseEntity<RetailStore> update(@RequestBody RetailStore retailStore) {
        RetailStore updated = service.update(retailStore);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/find/{storeNumber}")
    public ResponseEntity<Optional<RetailStore>> findByStoreNumber(@PathVariable String storeNumber) {
        Optional<RetailStore> retailStore = service.findByStoreNumber(storeNumber);
        if (retailStore.isPresent()) {
            return ResponseEntity.ok(retailStore);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetailStore>> getAll() {
        List<RetailStore> stores = service.getAll();
        return ResponseEntity.ok(stores);
    }

}