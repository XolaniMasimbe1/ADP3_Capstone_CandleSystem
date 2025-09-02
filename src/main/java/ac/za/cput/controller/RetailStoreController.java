package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.factory.RetailStoreFactory;
import ac.za.cput.service.RetailStoreService;
import ac.za.cput.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/store")
@Transactional
public class RetailStoreController {
    private final RetailStoreService service;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RetailStoreController(RetailStoreService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/create")
    public RetailStore create(@RequestBody RetailStore retailStore) {
        return service.create(retailStore);
    }

    @GetMapping("/read/{storeNumber}")
    public RetailStore read(@PathVariable String storeNumber) {
        return service.read(storeNumber);
    }

    @GetMapping("/read/id/{storeId}")
    public RetailStore readById(@PathVariable String storeId) {
        return service.readById(storeId);
    }

    @GetMapping("/read/user/{userId}")
    public RetailStore readByUserId(@PathVariable String userId) {
        return service.readByUserId(userId);
    }

    @PutMapping("/update")
    public RetailStore update(@RequestBody RetailStore retailStore) {
        return service.update(retailStore);
    }

    @GetMapping("/find/{storeNumber}")
    public Optional<RetailStore> findByStoreNumber(@PathVariable String storeNumber) {
        return service.findByStoreNumber(storeNumber);
    }

    @GetMapping("/all")
    public List<RetailStore> getAll() {
        return service.getAll();
    }

    @PostMapping("/register")
    public ResponseEntity<RetailStore> registerStore(@RequestBody RetailStore retailStore) {
        try {
            // Check if username exists
            if (retailStore.getUser() != null && retailStore.getUser().getUsername() != null) {
                if (userService.findByUsername(retailStore.getUser().getUsername()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Create RetailStore using RetailStoreFactory
                RetailStore newRetailStore = RetailStoreFactory.createRetailStore(
                        retailStore.getStoreName(),
                        retailStore.getUser().getUsername(),
                        "defaultPassword", // You can set a default or get from request
                        retailStore.getUser().getContactDetails().getEmail(),
                        retailStore.getUser().getContactDetails().getPhoneNumber(),
                        retailStore.getUser().getContactDetails().getPostalCode(),
                        retailStore.getUser().getContactDetails().getStreet(),
                        retailStore.getUser().getContactDetails().getCity(),
                        retailStore.getUser().getContactDetails().getProvince(),
                        retailStore.getUser().getContactDetails().getCountry()
                );
                
                if (newRetailStore == null) {
                    return ResponseEntity.badRequest().build();
                }

                // Save RetailStore
                RetailStore savedStore = service.create(newRetailStore);
                return ResponseEntity.ok(savedStore);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<RetailStore> login(@RequestBody RetailStore retailStore) {
        // Check if it's a retail store
        if (retailStore.getUser() != null && retailStore.getUser().getUsername() != null) {
            Optional<RetailStore> optionalStore = service.findByUsername(retailStore.getUser().getUsername());
            if (optionalStore.isPresent()) {
                RetailStore foundStore = optionalStore.get();
                if (passwordEncoder.matches("defaultPassword", foundStore.getUser().getPasswordHash())) {
                    return ResponseEntity.ok(foundStore);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}