package ac.za.cput.controller;
/*
 * RetailStoreController.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.RetailStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retailstore")
public class RetailStoreController {

    private final RetailStoreRepository retailStoreRepository;

    @Autowired
    public RetailStoreController(RetailStoreRepository retailStoreRepository) {
        this.retailStoreRepository = retailStoreRepository;
    }

    @PostMapping("/create")
    public RetailStore create(@RequestBody RetailStore retailStore) {
        return this.retailStoreRepository.save(retailStore);
    }

    @GetMapping("/read/{storeNumber}")
    public RetailStore read(@PathVariable String storeNumber) {
        return this.retailStoreRepository.findById(storeNumber)
                .orElse(null);
    }

    @PutMapping("/update")
    public RetailStore update(@RequestBody RetailStore retailStore) {
        return this.retailStoreRepository.save(retailStore);
    }

    @DeleteMapping("/delete/{storeNumber}")
    public void delete(@PathVariable String storeNumber) {
        this.retailStoreRepository.deleteById(storeNumber);
    }

    @GetMapping("/all")
    public Iterable<RetailStore> getAll() {
        return this.retailStoreRepository.findAll();
    }
}
