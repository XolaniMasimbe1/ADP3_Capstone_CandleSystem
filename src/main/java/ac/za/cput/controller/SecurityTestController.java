package ac.za.cput.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/security")
public class SecurityTestController {

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> userInfo = new HashMap<>();
        
        userInfo.put("name", auth.getName());
        userInfo.put("authorities", auth.getAuthorities());
        userInfo.put("authenticated", auth.isAuthenticated());
        
        return userInfo;
    }

    @GetMapping("/admin-only")
    public Map<String, String> adminOnly() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is an admin-only endpoint");
        response.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return response;
    }

    @GetMapping("/driver-only")
    public Map<String, String> driverOnly() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a driver-only endpoint");
        response.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return response;
    }

    @GetMapping("/retail-only")
    public Map<String, String> retailOnly() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "This is a retail store-only endpoint");
        response.put("user", SecurityContextHolder.getContext().getAuthentication().getName());
        return response;
    }
}
