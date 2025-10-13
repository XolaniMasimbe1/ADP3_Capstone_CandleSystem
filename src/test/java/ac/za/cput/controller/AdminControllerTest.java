package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.factory.AdminFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdminControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static Admin admin;

    private String baseURL() {
        return "http://localhost:" + port + "/CandleSystem";
    }

    private String adminURL() {
        return baseURL() + "/admin";
    }

    @BeforeAll
    void setUp() {
        // Create only one admin for testing
        admin = AdminFactory.createAdmin("admin", "Admin123!", "admin@ezelina.com", "0839876543");
        assertNotNull(admin);
    }

    @AfterAll
    void tearDown() {
        // Clean up test data
        try {
            if (admin != null && admin.getAdminId() != null) {
                restTemplate.exchange(
                        adminURL() + "/delete/" + admin.getAdminId(),
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
                System.out.println("Cleaned up admin");
            }
        } catch (Exception e) {
            System.out.println("Error cleaning up test admin: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void a_create() {
        System.out.println("Creating admin: " + admin);
        System.out.println("Admin ID: " + admin.getAdminId());
        System.out.println("Admin Username: " + admin.getUsername());
        System.out.println("Admin Email: " + admin.getEmail());
        
        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/create",
                admin,
                Admin.class
        );

        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
        
        assertNotNull(response.getBody());
        admin = response.getBody();
        assertEquals(admin.getAdminId(), response.getBody().getAdminId());
        System.out.println("Created: " + admin);
    }

    @Test
    @Order(2)
    void b_read() {
        System.out.println("Reading admin with ID: " + admin.getAdminId());
        
        ResponseEntity<Admin> response = restTemplate.getForEntity(
                adminURL() + "/read/" + admin.getAdminId(),
                Admin.class
        );

        System.out.println("Read Response Status: " + response.getStatusCode());
        System.out.println("Read Response Body: " + response.getBody());
        
        assertNotNull(response.getBody());
        assertEquals(admin.getAdminId(), response.getBody().getAdminId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_login() {
        // Create login request with correct credentials using Map for auth endpoint
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", admin.getEmail());
        loginRequest.put("password", "Admin123!"); // Admin password

        System.out.println("Login attempt for: " + loginRequest.get("email"));
        System.out.println("Login password: " + loginRequest.get("password"));
        System.out.println("Admin in database: " + admin);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                baseURL() + "/auth/admin/login",
                loginRequest,
                Map.class
        );

        System.out.println("Login Response Status: " + response.getStatusCode());
        System.out.println("Login Response Body: " + response.getBody());
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("token"));
        System.out.println("Admin Login successful: " + response.getBody());
    }
}