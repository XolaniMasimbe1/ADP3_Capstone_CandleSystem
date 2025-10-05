package ac.za.cput.controller;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.ContactPerson;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.factory.RetailStoreFactory;
import ac.za.cput.service.RetailStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('RETAIL_STORE')")
    public RetailStore create(@RequestBody RetailStore retailStore) {
        return service.create(retailStore);
    }

    @GetMapping("/read/{storeId}")
    public RetailStore read(@PathVariable String storeId) {
        return service.read(storeId);
    }

    @GetMapping("/read/id/{storeId}")
    public RetailStore readById(@PathVariable String storeId) {
        return service.readById(storeId);
    }


    @PutMapping("/update")
    public ResponseEntity<RetailStore> update(@RequestBody RetailStore retailStore) {
        try {
            // Check for unique constraint violations before updating
            if (retailStore.getStoreEmail() != null) {
                // You might want to add a check here to see if email already exists
                // for a different store
            }
            
            RetailStore updatedStore = service.update(retailStore);
            return ResponseEntity.ok(updatedStore);
        } catch (Exception e) {
            System.err.println("Error in update: " + e.getMessage());
            e.printStackTrace();
            
            // Check for specific constraint violations
            if (e.getMessage() != null && e.getMessage().contains("unique constraint")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/update/{storeId}")
    @SuppressWarnings("unchecked")
    public ResponseEntity<RetailStore> partialUpdate(@PathVariable String storeId, @RequestBody Map<String, Object> updates) {
        try {
            RetailStore existingStore = service.readById(storeId);
            if (existingStore == null) {
                return ResponseEntity.notFound().build();
            }

            // Update only the fields that are provided
            if (updates.containsKey("storeName")) {
                existingStore.setStoreName((String) updates.get("storeName"));
            }
            if (updates.containsKey("storeEmail")) {
                String newEmail = (String) updates.get("storeEmail");
                // Check if email is different to avoid unique constraint violation
                if (!newEmail.equals(existingStore.getStoreEmail())) {
                    existingStore.setStoreEmail(newEmail);
                }
            }
            if (updates.containsKey("passwordHash")) {
                existingStore.setPasswordHash((String) updates.get("passwordHash"));
            }
            
            // Update address if provided
            if (updates.containsKey("address")) {
                @SuppressWarnings("unchecked")
                Map<String, Object> addressData = (Map<String, Object>) updates.get("address");
                if (existingStore.getAddress() != null) {
                    if (addressData.containsKey("streetNumber")) {
                        existingStore.getAddress().setStreetNumber((String) addressData.get("streetNumber"));
                    }
                    if (addressData.containsKey("streetName")) {
                        existingStore.getAddress().setStreetName((String) addressData.get("streetName"));
                    }
                    if (addressData.containsKey("suburb")) {
                        existingStore.getAddress().setSuburb((String) addressData.get("suburb"));
                    }
                    if (addressData.containsKey("city")) {
                        existingStore.getAddress().setCity((String) addressData.get("city"));
                    }
                    if (addressData.containsKey("province")) {
                        String provinceString = (String) addressData.get("province");
                        try {
                            Province province = Province.valueOf(provinceString);
                            existingStore.getAddress().setProvince(province);
                        } catch (IllegalArgumentException e) {
                            // If the province string doesn't match any enum value, skip the update
                            System.err.println("Invalid province value: " + provinceString);
                        }
                    }
                    if (addressData.containsKey("postalCode")) {
                        existingStore.getAddress().setPostalCode((String) addressData.get("postalCode"));
                    }
                    if (addressData.containsKey("country")) {
                        existingStore.getAddress().setCountry((String) addressData.get("country"));
                    }
                }
            }

            RetailStore updatedStore = service.update(existingStore);
            return ResponseEntity.ok(updatedStore);
        } catch (Exception e) {
            System.err.println("Error in partial update: " + e.getMessage());
            e.printStackTrace();
            
            // Check for specific constraint violations
            if (e.getMessage() != null && e.getMessage().contains("unique constraint")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // Return conflict status for unique constraint violations
            }
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/find/{storeId}")
    public Optional<RetailStore> findByStoreId(@PathVariable String storeId) {
        return service.findByStoreId(storeId);
    }

    @GetMapping("/all")
    public List<RetailStore> getAll() {
        return service.getAll();
    }

        @PostMapping("/register")
        public ResponseEntity<?> registerStore(@RequestBody RetailStore retailStore) {
            try {
                // Check if store email exists
                if (service.findByStoreEmail(retailStore.getStoreEmail()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Password will be hashed in RetailStoreFactory, so pass the raw password
                System.out.println("Registration - Original password: " + retailStore.getPasswordHash());
                System.out.println("Registration - Password length: " + retailStore.getPasswordHash().length());
                
                // Pass the raw password to the factory (it will handle hashing)
                String rawPassword = retailStore.getPasswordHash();
                
                // Create RetailStore with Contact relationship
                RetailStore newRetailStore = RetailStoreFactory.createRetailStore(
                        retailStore.getStoreName(),
                        retailStore.getStoreEmail(),
                        rawPassword, // Pass raw password - factory will hash it
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
                        .setStoreName(savedStore.getStoreName())
                        .setStoreEmail(savedStore.getStoreEmail())
                        .setAddress(savedStore.getAddress())
                        .setContactPersons(savedStore.getContactPersons())
                        .build();
                
                return ResponseEntity.ok(responseStore);
            } catch (Exception e) {
                e.printStackTrace(); // Log the exception for debugging
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Registration failed: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody RetailStore loginRequest) {
            try {
                System.out.println("Login attempt for email: " + loginRequest.getStoreEmail());
                
                Optional<RetailStore> optionalStore = service.findByStoreEmail(loginRequest.getStoreEmail());
                if (optionalStore.isPresent()) {
                    RetailStore foundStore = optionalStore.get();
                    System.out.println("Store found: " + foundStore.getStoreName());
                    System.out.println("Stored password hash: " + foundStore.getPasswordHash());
                    System.out.println("Login password: " + loginRequest.getPasswordHash());

                    boolean passwordMatches = passwordEncoder.matches(loginRequest.getPasswordHash(), foundStore.getPasswordHash());
                    
                    if (passwordMatches) {
                        // Check if retail store account is active
                        if (!foundStore.isActive()) {
                            System.out.println("Retail store account is blocked: " + foundStore.getStoreEmail());
                            Map<String, String> errorResponse = new HashMap<>();
                            errorResponse.put("error", "Account is blocked. Please contact administrator.");
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
                        }
                        System.out.println("Password matches - login successful");
                        return ResponseEntity.ok(foundStore);
                    } else {
                        System.out.println("Password does not match - login failed");
                        // SECURITY: Do NOT reset passwords automatically - this is a major security vulnerability
                        // If password doesn't match, login should fail
                    }
                } else {
                    System.out.println("Store not found for email: " + loginRequest.getStoreEmail());

                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Store not found. Please check your email address.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
                }
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Invalid credentials");
                return ResponseEntity.badRequest().body(errorResponse);
            } catch (Exception e) {
                e.printStackTrace();
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Login failed: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
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

        // Block retail store account
        @PostMapping("/block/{storeId}")
        public ResponseEntity<?> blockRetailStore(@PathVariable String storeId) {
            try {
                RetailStore store = service.read(storeId);
                if (store != null) {
                    store.setActive(false);
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                System.err.println("Error blocking retail store: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // Unblock retail store account
        @PostMapping("/unblock/{storeId}")
        public ResponseEntity<?> unblockRetailStore(@PathVariable String storeId) {
            try {
                RetailStore store = service.read(storeId);
                if (store != null) {
                    store.setActive(true);
                    RetailStore updatedStore = service.update(store);
                    return ResponseEntity.ok(updatedStore);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                System.err.println("Error unblocking retail store: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

    }