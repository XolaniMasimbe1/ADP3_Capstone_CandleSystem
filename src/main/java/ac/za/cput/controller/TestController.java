package ac.za.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = "*") // Allow all origins for development
public class TestController {

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This is a public endpoint. Anyone can access it.");
    }

    @GetMapping("/protected")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("This is a protected endpoint. Only authenticated users can access it.");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("This is an admin-only endpoint. Only users with ADMIN role can access it.");
    }

    @GetMapping("/store")
    @PreAuthorize("hasRole('RETAIL_STORE')")
    public ResponseEntity<String> retailStoreEndpoint() {
        return ResponseEntity.ok("This is a retail store-only endpoint. Only users with RETAIL_STORE role can access it.");
    }
}