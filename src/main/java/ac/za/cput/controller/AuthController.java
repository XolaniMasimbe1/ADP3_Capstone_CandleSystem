package ac.za.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Please use the API endpoints for authentication: /admin/login, /driver/login, or /store/login");
        response.put("status", "info");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> dashboard() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Dashboard endpoint - use your frontend application");
        response.put("status", "info");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Candle System API is running");
        response.put("status", "success");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }
}
