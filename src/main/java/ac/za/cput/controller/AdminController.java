package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.factory.AdminFactory;
import ac.za.cput.service.AdminService;
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
@RequestMapping("/admin")
@Transactional
public class AdminController {
    private final AdminService service;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminController(AdminService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Admin create(@RequestBody Admin admin) {
        // Hash the password before saving
        if (admin.getPasswordHash() != null && !admin.getPasswordHash().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(admin.getPasswordHash());
            admin.setPasswordHash(hashedPassword);
        }
        return service.create(admin);
    }

    @GetMapping("/read/{adminId}")
    public Admin read(@PathVariable String adminId) {
        return service.read(adminId);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Admin update(@RequestBody Admin admin) {
        return service.update(admin);
    }

    @PostMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody Admin admin) {
        try {
            // Find the admin by username or email
            Optional<Admin> optionalAdmin = service.findByUsername(admin.getUsername());
            if (!optionalAdmin.isPresent()) {
                optionalAdmin = service.findByEmail(admin.getUsername());
            }
            
            if (optionalAdmin.isPresent()) {
                Admin existingAdmin = optionalAdmin.get();
                // Hash the new password
                String hashedPassword = passwordEncoder.encode(admin.getPasswordHash());
                existingAdmin.setPasswordHash(hashedPassword);
                service.update(existingAdmin);
                return ResponseEntity.ok("Password updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Admin not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating password");
        }
    }

    @DeleteMapping("/delete/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable String adminId) {
        boolean deleted = service.delete(adminId);
        if (deleted) {
            return ResponseEntity.ok("Admin deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/username/{username}")
    public Optional<Admin> findByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @GetMapping("/find/email/{email}")
    public Optional<Admin> findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/all")
    public List<Admin> getAll() {
        return service.getAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) {
        try {
            // Validate input
            if (admin.getUsername() == null || admin.getUsername().trim().isEmpty()) {
                System.out.println("Registration failed: Username is null or empty");
                return ResponseEntity.badRequest().build();
            }
            
            if (admin.getEmail() == null || admin.getEmail().trim().isEmpty()) {
                System.out.println("Registration failed: Email is null or empty");
                return ResponseEntity.badRequest().build();
            }
            
            if (admin.getPasswordHash() == null || admin.getPasswordHash().trim().isEmpty()) {
                System.out.println("Registration failed: Password is null or empty");
                return ResponseEntity.badRequest().build();
            }

            // Check if admin username exists
            if (service.findByUsername(admin.getUsername()).isPresent()) {
                System.out.println("Registration failed: Username already exists: " + admin.getUsername());
                return ResponseEntity.badRequest().build();
            }

            // Check if admin email exists
            if (service.findByEmail(admin.getEmail()).isPresent()) {
                System.out.println("Registration failed: Email already exists: " + admin.getEmail());
                return ResponseEntity.badRequest().build();
            }

            // Hash the password
            String hashedPassword = passwordEncoder.encode(admin.getPasswordHash());

            // Create admin using factory to generate ID
            Admin newAdmin = AdminFactory.createAdmin(
                admin.getUsername(),
                hashedPassword,
                admin.getEmail(),
                admin.getPhoneNumber()
            );

            if (newAdmin == null) {
                System.out.println("Registration failed: Could not create admin");
                return ResponseEntity.badRequest().build();
            }

            Admin savedAdmin = service.create(newAdmin);
            System.out.println("Admin registered successfully: " + savedAdmin.getUsername());
            return ResponseEntity.ok(savedAdmin);
        } catch (Exception e) {
            System.err.println("Registration error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Admin loginRequest) {
        try {
            System.out.println("Login attempt for email: " + loginRequest.getEmail());
            
            // Find admin by email only
            Optional<Admin> optionalAdmin = service.findByEmail(loginRequest.getEmail());
            
            if (optionalAdmin.isPresent()) {
                Admin foundAdmin = optionalAdmin.get();
                System.out.println("Found admin: " + foundAdmin.getEmail());
                
                // Verify password
                boolean passwordMatches = passwordEncoder.matches(loginRequest.getPasswordHash(), foundAdmin.getPasswordHash());
                System.out.println("Password matches: " + passwordMatches);
                
                if (passwordMatches) {
                    // Check if admin account is active
                    if (!foundAdmin.isActive()) {
                        System.out.println("Admin account is blocked: " + foundAdmin.getEmail());
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Account is blocked. Please contact administrator.");
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
                    }
                    System.out.println("Login successful for: " + foundAdmin.getEmail());
                    return ResponseEntity.ok(foundAdmin);
                } else {
                    System.out.println("Password verification failed for: " + foundAdmin.getEmail());
                }
            } else {
                System.out.println("No admin found with email: " + loginRequest.getEmail());
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Admin not found. Please check your email address.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            return ResponseEntity.badRequest().body(errorResponse);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Block admin account
    @PostMapping("/block/{adminId}")
    public ResponseEntity<?> blockAdmin(@PathVariable String adminId) {
        try {
            Admin admin = service.read(adminId);
            if (admin != null) {
                admin.setActive(false);
                Admin updatedAdmin = service.update(admin);
                return ResponseEntity.ok(updatedAdmin);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error blocking admin: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Unblock admin account
    @PostMapping("/unblock/{adminId}")
    public ResponseEntity<?> unblockAdmin(@PathVariable String adminId) {
        try {
            Admin admin = service.read(adminId);
            if (admin != null) {
                admin.setActive(true);
                Admin updatedAdmin = service.update(admin);
                return ResponseEntity.ok(updatedAdmin);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error unblocking admin: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
