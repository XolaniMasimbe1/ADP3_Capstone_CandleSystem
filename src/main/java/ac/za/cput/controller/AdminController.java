package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.service.AdminService;
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
        return service.create(admin);
    }

    @GetMapping("/read/{adminId}")
    public Admin read(@PathVariable String adminId) {
        return service.read(adminId);
    }

    @PutMapping("/update")
    public Admin update(@RequestBody Admin admin) {
        return service.update(admin);
    }

    @DeleteMapping("/delete/{adminId}")
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
            // Check if admin username exists
            if (service.findByUsername(admin.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            // Check if admin email exists
            if (service.findByEmail(admin.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            // Hash the password
            admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));

            Admin savedAdmin = service.create(admin);
            return ResponseEntity.ok(savedAdmin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin loginRequest) {
        try {
            // Find admin by username
            Optional<Admin> optionalAdmin = service.findByUsername(loginRequest.getUsername());
            if (optionalAdmin.isPresent()) {
                Admin foundAdmin = optionalAdmin.get();
                // Verify password
                if (passwordEncoder.matches(loginRequest.getPasswordHash(), foundAdmin.getPasswordHash())) {
                    return ResponseEntity.ok(foundAdmin);
                }
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
