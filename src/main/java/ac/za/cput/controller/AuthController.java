package ac.za.cput.controller;

import ac.za.cput.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*") // Allow all origins for development
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            
            System.out.println(" AuthController - Universal login request received");
            
            Map<String, Object> response = authenticationService.login(email, password);
            
            System.out.println("AuthController - Universal login successful");
            System.out.println("   JWT Token: " + response.get("token"));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("AuthController - Universal login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<Map<String, Object>> adminLogin(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            
            System.out.println(" AuthController - Admin login request received");
            
            Map<String, Object> response = authenticationService.loginAdmin(email, password);
            
            System.out.println("AuthController - Admin login successful");
            System.out.println("   JWT Token: " + response.get("token"));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("AuthController - Admin login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/store/login")
    public ResponseEntity<Map<String, Object>> storeLogin(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");
            
            System.out.println("AuthController - Store login request received");
            
            Map<String, Object> response = authenticationService.loginRetailStore(email, password);
            
            System.out.println("AuthController - Store login successful");
            System.out.println("   JWT Token: " + response.get("token"));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("AuthController - Store login failed: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}