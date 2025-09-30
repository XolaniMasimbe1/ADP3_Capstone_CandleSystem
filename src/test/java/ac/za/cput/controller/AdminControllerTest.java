package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.factory.AdminFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

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
    private static Admin admin2;

    private String baseURL() {
        return "http://localhost:" + port + "/CandleSystem";
    }

    private String adminURL() {
        return baseURL() + "/admin";
    }

    @BeforeAll
    void setUp() {
        // Create unique test data using timestamp to avoid conflicts
        long timestamp = System.currentTimeMillis();
        String uniqueUsername = "admin1_" + timestamp;
        String uniqueEmail = "admin_" + timestamp + "@test.com";
        String uniqueUsername2 = "admin2_" + timestamp;
        String uniqueEmail2 = "admin2_" + timestamp + "@test.com";
        
        // Create test admin
        admin = AdminFactory.createAdmin(uniqueUsername, uniqueEmail, "1234567890");
        assertNotNull(admin);
        
        // Create second admin for testing
        admin2 = AdminFactory.createAdmin(uniqueUsername2, uniqueEmail2, "0987654321");
        assertNotNull(admin2);
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
                System.out.println("Cleaned up main test admin");
            }
        } catch (Exception e) {
            System.out.println("Error cleaning up main test admin: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/create",
                admin,
                Admin.class
        );

        assertNotNull(response.getBody());
        admin = response.getBody();
        assertEquals(admin.getAdminId(), response.getBody().getAdminId());
        System.out.println("Created: " + admin);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Admin> response = restTemplate.getForEntity(
                adminURL() + "/read/" + admin.getAdminId(),
                Admin.class
        );

        assertNotNull(response.getBody());
        assertEquals(admin.getAdminId(), response.getBody().getAdminId());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_register() {
        // Create unique admin for registration test
        long timestamp = System.currentTimeMillis();
        String uniqueUsername = "newadmin_" + timestamp;
        String uniqueEmail = "newadmin_" + timestamp + "@test.com";
        
        Admin newAdmin = AdminFactory.createAdmin(uniqueUsername, uniqueEmail, "5555555555");
        assertNotNull(newAdmin);

        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/register",
                newAdmin,
                Admin.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(uniqueUsername, response.getBody().getUsername());
        System.out.println("Registered: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_login() {
        // Create login request with correct credentials
        Admin loginRequest = new Admin();
        loginRequest.setUsername(admin.getUsername());
        loginRequest.setPasswordHash("admin123"); // Default password from factory

        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/login",
                loginRequest,
                Admin.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getUsername(), response.getBody().getUsername());
        System.out.println("Login successful: " + response.getBody());
    }

    @Test
    @Order(5)
    void e_getAll() {
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(
                adminURL() + "/all",
                Admin[].class
        );

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Admins (" + response.getBody().length + " total)");
    }
}