package ac.za.cput.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "Backend is running!";
    }

    @GetMapping("/security")
    public String securityTest() {
        return "Security configuration is working!";
    }
}
