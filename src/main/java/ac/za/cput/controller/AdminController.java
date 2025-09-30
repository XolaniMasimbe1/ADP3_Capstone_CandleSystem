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

import java.util.List;
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
    @PreAuthorize("hasRole('ADMIN')")
    public Admin create(@RequestBody Admin admin) {
        return service.create(admin);
    }

    @GetMapping("/read/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Admin read(@PathVariable String adminId) {
        return service.read(adminId);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public Admin update(@RequestBody Admin admin) {
        return service.update(admin);
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
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<Admin> login(@RequestBody Admin loginRequest) {
        try {
            System.out.println("Login attempt for username/email: " + loginRequest.getUsername());
            
            // Try to find admin by username first
            Optional<Admin> optionalAdmin = service.findByUsername(loginRequest.getUsername());
            
            // If not found by username, try by email
            if (!optionalAdmin.isPresent()) {
                System.out.println("No admin found with username, trying email: " + loginRequest.getUsername());
                optionalAdmin = service.findByEmail(loginRequest.getUsername());
            }
            
            if (optionalAdmin.isPresent()) {
                Admin foundAdmin = optionalAdmin.get();
                System.out.println("Found admin: " + foundAdmin.getUsername() + " (email: " + foundAdmin.getEmail() + ")");
                System.out.println("Stored password hash: " + foundAdmin.getPasswordHash());
                System.out.println("Provided password: " + loginRequest.getPasswordHash());
                
                // Verify password
                boolean passwordMatches = passwordEncoder.matches(loginRequest.getPasswordHash(), foundAdmin.getPasswordHash());
                System.out.println("Password matches: " + passwordMatches);
                
                if (passwordMatches) {
                    System.out.println("Login successful for: " + foundAdmin.getUsername());
                    return ResponseEntity.ok(foundAdmin);
                } else {
                    System.out.println("Password verification failed for: " + foundAdmin.getUsername());
                }
            } else {
                System.out.println("No admin found with username or email: " + loginRequest.getUsername());
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
