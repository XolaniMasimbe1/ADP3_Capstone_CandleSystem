package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.service.RetailStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/retailstore")
public class RetailStoreController {

    private final RetailStoreService retailStoreService;

    @Autowired
    public RetailStoreController(RetailStoreService retailStoreService) {
        this.retailStoreService = retailStoreService;
    }

    @PostMapping("/create")
    public RetailStore create(@RequestBody RetailStore retailStore) {
        return retailStoreService.create(retailStore);
    }

    @GetMapping("/read/{storeNumber}")
    public RetailStore read(@PathVariable String storeNumber) {
        return retailStoreService.read(storeNumber);
    }

    @PostMapping("/update")
    public RetailStore update(@RequestBody RetailStore retailStore) {
        return retailStoreService.update(retailStore);
    }

    @GetMapping("/find/{storeNumber}")
    public Optional<RetailStore> findByStoreNumber(@PathVariable String storeNumber) {
        return retailStoreService.findByStoreNumber(storeNumber);
    }

    @GetMapping("/all")
    public List<RetailStore> getAll() {
        return retailStoreService.getAll();  // support listing all
    }
}
