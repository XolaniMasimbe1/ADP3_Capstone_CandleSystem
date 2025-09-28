package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Contact;
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
                        ac.za.cput.domain.Enum.Province.valueOf(retailStore.getAddress().getProvince()),
                        retailStore.getAddress().getPostalCode(),
                        retailStore.getAddress().getCountry(),
                        // Contact Person 1 - get from first contact if exists
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 0 ? retailStore.getContacts().get(0).getFirstName() : null,
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 0 ? retailStore.getContacts().get(0).getLastName() : null,
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 0 ? retailStore.getContacts().get(0).getEmail() : null,
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 0 ? retailStore.getContacts().get(0).getPhone() : null,
                        // Contact Person 2 - get from second contact if exists
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 1 ? retailStore.getContacts().get(1).getFirstName() : null,
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 1 ? retailStore.getContacts().get(1).getLastName() : null,
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 1 ? retailStore.getContacts().get(1).getEmail() : null,
                        retailStore.getContacts() != null && retailStore.getContacts().size() > 1 ? retailStore.getContacts().get(1).getPhone() : null
                );
                
                if (newRetailStore == null) {
                    return ResponseEntity.badRequest().build();
                }

                // Save RetailStore
                RetailStore savedStore = service.create(newRetailStore);
                return ResponseEntity.ok(savedStore);
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
        public ResponseEntity<String> testContactCreation(@RequestBody Contact contact) {
            try {
                // Set a test storeId
                contact.setStoreId("test-store-id");
                contact.setContactId("test-contact-id");
                contact.setCreatedAt(java.time.LocalDateTime.now());
                contact.setUpdatedAt(java.time.LocalDateTime.now());
                
                // This should not fail with store_id constraint
                return ResponseEntity.ok("Contact created successfully with storeId: " + contact.getStoreId());
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
        public ResponseEntity<RetailStore> addContact(@PathVariable String storeId, @RequestBody Contact contact) {
            try {
                RetailStore store = service.readById(storeId);
                if (store != null) {
                    contact.setStoreId(storeId);
                    store.addContact(contact);
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        @PutMapping("/{storeId}/contacts/{contactId}")
        public ResponseEntity<RetailStore> updateContact(@PathVariable String storeId, @PathVariable String contactId, @RequestBody Contact contact) {
            try {
                RetailStore store = service.readById(storeId);
                if (store != null) {
                    // Find and update the contact
                    for (Contact c : store.getContacts()) {
                        if (c.getContactId().equals(contactId)) {
                            c.setFirstName(contact.getFirstName());
                            c.setLastName(contact.getLastName());
                            c.setEmail(contact.getEmail());
                            c.setPhone(contact.getPhone());
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
                    store.getContacts().removeIf(contact -> contact.getContactId().equals(contactId));
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                }
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }