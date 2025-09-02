package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.factory.AdminFactory;
import ac.za.cput.service.AdminService;
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
@RequestMapping("/admin")
@Transactional
public class AdminController {
    private final AdminService service;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminController(AdminService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        Admin createdAdmin = service.create(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmin);
    }

    @GetMapping("/read/{adminId}")
    public ResponseEntity<Admin> read(@PathVariable String adminId) {
        Admin admin = service.read(adminId);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {
        Admin updatedAdmin = service.update(admin);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<Void> delete(@PathVariable String adminId) {
        boolean deleted = service.delete(adminId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Admin> findByUsername(@PathVariable String username) {
        Optional<Admin> admin = service.findByUsername(username);
        return admin.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAll() {
        List<Admin> allAdmins = service.getAll();
        return ResponseEntity.ok(allAdmins);
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
        try {
            // Check if username exists
            if (admin.getUser() != null && admin.getUser().getUsername() != null) {
                if (userService.findByUsername(admin.getUser().getUsername()).isPresent() || 
                    service.findByUsername(admin.getUser().getUsername()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Check if email exists
                if (admin.getUser().getContactDetails() != null && 
                    service.findByEmail(admin.getUser().getContactDetails().getEmail()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Create admin using AdminFactory
                Admin newAdmin = AdminFactory.createAdmin(
                        admin.getUser().getUsername(),
                        "defaultPassword", // You can set a default or get from request
                        admin.getUser().getContactDetails().getEmail(),
                        admin.getUser().getContactDetails().getPhoneNumber(),
                        admin.getUser().getContactDetails().getPostalCode(),
                        admin.getUser().getContactDetails().getStreet(),
                        admin.getUser().getContactDetails().getCity(),
                        admin.getUser().getContactDetails().getProvince(),
                        admin.getUser().getContactDetails().getCountry()
                );
                
                Admin savedAdmin = service.create(newAdmin);
                return ResponseEntity.ok(savedAdmin);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Admin admin) {
        // Check if it's an admin
        if (admin.getUser() != null && admin.getUser().getUsername() != null) {
            Optional<Admin> optionalAdmin = service.findByUsername(admin.getUser().getUsername());
            if (optionalAdmin.isPresent()) {
                Admin foundAdmin = optionalAdmin.get();
                if (passwordEncoder.matches("defaultPassword", foundAdmin.getUser().getPasswordHash())) {
                    return ResponseEntity.ok(foundAdmin);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
