package ac.za.cput.service;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.AdminRepository;
import ac.za.cput.repository.RetailStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Simple Authentication Service
 * Handles login for both Admin and RetailStore entities
 */
@Service
public class AuthenticationService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private RetailStoreRepository retailStoreRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    /**
     * Login for Admin
     */
    public Map<String, Object> loginAdmin(String email, String password) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        
        if (adminOpt.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }
        
        Admin admin = adminOpt.get();
        
        // Check password
        if (!passwordEncoder.matches(password, admin.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        
        // Check if admin account is active
        if (!admin.isActive()) {
            throw new RuntimeException("Account is blocked. Please contact administrator.");
        }
        
        // Generate JWT token using User interface
        System.out.println("=== ADMIN JWT TOKEN GENERATION ===");
        
        String token = jwtService.generateToken(
            admin.getId(),
            admin.getEmail(),
            admin.getRole(),
            admin.getName()
        );
        
        System.out.println("Generated JWT Token: " + token);
        System.out.println("=== END ADMIN JWT GENERATION ===");
        
        // Return response using User interface
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of(
            "id", admin.getId(),
            "email", admin.getEmail(),
            "name", admin.getName(),
            "role", admin.getRole()
        ));
        
        return response;
    }
    
    /**
     * Login for RetailStore
     */
    public Map<String, Object> loginRetailStore(String email, String password) {
        Optional<RetailStore> storeOpt = retailStoreRepository.findByStoreEmail(email);
        
        if (storeOpt.isEmpty()) {
            throw new RuntimeException("Store not found");
        }
        
        RetailStore store = storeOpt.get();
        
        // Check password
        if (!passwordEncoder.matches(password, store.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }
        
        // Check if store account is active
        if (!store.isActive()) {
            throw new RuntimeException("Account is blocked. Please contact administrator.");
        }
        
        // Generate JWT token using User interface
        System.out.println("=== RETAIL STORE JWT TOKEN GENERATION ===");
        
        String token = jwtService.generateToken(
            store.getId(),
            store.getEmail(),
            store.getRole(),
            store.getName()
        );
        
        System.out.println("Generated JWT Token: " + token);
        System.out.println("=== END RETAIL STORE JWT GENERATION ===");
        
        // Return response using User interface
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", Map.of(
            "id", store.getId(),
            "email", store.getEmail(),
            "name", store.getName(),
            "role", store.getRole()
        ));
        
        return response;
    }
    
    /**
     * Universal login method that tries both Admin and RetailStore
     */
    public Map<String, Object> login(String email, String password) {
        try {
            // Try Admin first
            return loginAdmin(email, password);
        } catch (RuntimeException e) {
            try {
                // Try RetailStore
                return loginRetailStore(email, password);
            } catch (RuntimeException e2) {
                throw new RuntimeException("Invalid credentials");
            }
        }
    }
}
