package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.ContactPerson;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.factory.RetailStoreFactory;
import ac.za.cput.service.RetailStoreService;
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
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public RetailStoreController(RetailStoreService service) {
        this.service = service;
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
                // Check if store email exists
                if (service.findByStoreEmail(retailStore.getStoreEmail()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Create RetailStore with Contact relationship
                RetailStore newRetailStore = RetailStoreFactory.createRetailStore(
                        retailStore.getStoreName(),
                        retailStore.getStoreEmail(),
                        retailStore.getPasswordHash(), // This is actually the plain password from frontend
                        retailStore.getAddress().getStreetNumber(),
                        retailStore.getAddress().getStreetName(),
                        retailStore.getAddress().getSuburb(),
                        retailStore.getAddress().getCity(),
                        retailStore.getAddress().getProvince(),
                        retailStore.getAddress().getPostalCode(),
                        retailStore.getAddress().getCountry(),
                        // Contact Person 1 - get from first contact if exists
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 0 ? retailStore.getContactPersons().get(0).getFirstName() : null,
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 0 ? retailStore.getContactPersons().get(0).getLastName() : null,
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 0 ? retailStore.getContactPersons().get(0).getEmailAddress() : null,
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 0 ? retailStore.getContactPersons().get(0).getCellPhoneNumber() : null,
                        // Contact Person 2 - get from second contact if exists
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 1 ? retailStore.getContactPersons().get(1).getFirstName() : null,
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 1 ? retailStore.getContactPersons().get(1).getLastName() : null,
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 1 ? retailStore.getContactPersons().get(1).getEmailAddress() : null,
                        retailStore.getContactPersons() != null && retailStore.getContactPersons().size() > 1 ? retailStore.getContactPersons().get(1).getCellPhoneNumber() : null
                );
                
                if (newRetailStore == null) {
                    return ResponseEntity.badRequest().build();
                }

                // Save RetailStore
                RetailStore savedStore = service.create(newRetailStore);
                
                // Create a simplified response to avoid circular references
                RetailStore responseStore = new RetailStore.Builder()
                        .setStoreId(savedStore.getStoreId())
                        .setStoreNumber(savedStore.getStoreNumber())
                        .setStoreName(savedStore.getStoreName())
                        .setStoreEmail(savedStore.getStoreEmail())
                        .setAddress(savedStore.getAddress())
                        .setContactPersons(savedStore.getContactPersons())
                        .build();
                
                return ResponseEntity.ok(responseStore);
            } catch (Exception e) {
                e.printStackTrace(); // Log the exception for debugging
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PostMapping("/login")
        public ResponseEntity<RetailStore> login(@RequestBody RetailStore loginRequest) {
            try {
                // Use storeEmail as username for login (Mr. Naidoo's requirement)
                Optional<RetailStore> optionalStore = service.findByStoreEmail(loginRequest.getStoreEmail());
                if (optionalStore.isPresent()) {
                    RetailStore foundStore = optionalStore.get();
                    // Verify password
                    if (passwordEncoder.matches(loginRequest.getPasswordHash(), foundStore.getPasswordHash())) {
                        return ResponseEntity.ok(foundStore);
                    }
                }
                return ResponseEntity.badRequest().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @GetMapping("/provinces")
        public ResponseEntity<Province[]> getProvinces() {
            return ResponseEntity.ok(Province.values());
        }

        // Test endpoint to verify contact creation
        @PostMapping("/test-contact")
        public ResponseEntity<String> testContactCreation(@RequestBody ContactPerson contactPerson) {
            try {
                // Set a test storeId
                contactPerson.setContactPersonId("test-contact-id");
                
                // This should not fail with store_id constraint
                return ResponseEntity.ok("Contact person created successfully with ID: " + contactPerson.getContactPersonId());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
            }
        }

        @GetMapping("/{storeId}/contacts")
        public ResponseEntity<RetailStore> getContacts(@PathVariable String storeId) {
            try {
                RetailStore store = service.readById(storeId);
                if (store != null) {
                    return ResponseEntity.ok(store);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // Dynamic Contact Management Endpoints
        @PostMapping("/{storeId}/contacts")
        public ResponseEntity<RetailStore> addContact(@PathVariable String storeId, @RequestBody ContactPerson contactPerson) {
            try {
                RetailStore store = service.readById(storeId);
                if (store != null) {
                    store.addContactPerson(contactPerson);
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PutMapping("/{storeId}/contacts/{contactId}")
        public ResponseEntity<RetailStore> updateContact(@PathVariable String storeId, @PathVariable String contactId, @RequestBody ContactPerson contactPerson) {
            try {
                RetailStore store = service.readById(storeId);
                if (store != null) {
                    // Find and update the contact person
                    for (ContactPerson c : store.getContactPersons()) {
                        if (c.getContactPersonId().equals(contactId)) {
                            c.setFirstName(contactPerson.getFirstName());
                            c.setLastName(contactPerson.getLastName());
                            c.setEmailAddress(contactPerson.getEmailAddress());
                            c.setCellPhoneNumber(contactPerson.getCellPhoneNumber());
                            break;
                        }
                    }
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @DeleteMapping("/{storeId}/contacts/{contactId}")
        public ResponseEntity<RetailStore> deleteContact(@PathVariable String storeId, @PathVariable String contactId) {
            try {
                RetailStore store = service.readById(storeId);
                if (store != null) {
                    store.getContactPersons().removeIf(contactPerson -> contactPerson.getContactPersonId().equals(contactId));
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }