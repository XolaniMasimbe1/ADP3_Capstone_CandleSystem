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
        // Create super admin with real data
        admin = AdminFactory.createAdmin("superadmin", "SuperAdmin123!", "superadmin@ezelina.com", "0821234567");
        assertNotNull(admin);
        
        // Create regular admin for testing
        admin2 = AdminFactory.createAdmin("admin", "Admin123!", "admin@ezelina.com", "0839876543");
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
                System.out.println("Cleaned up super admin");
            }
            
            if (admin2 != null && admin2.getAdminId() != null) {
                restTemplate.exchange(
                        adminURL() + "/delete/" + admin2.getAdminId(),
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
                System.out.println("Cleaned up regular admin");
            }
        } catch (Exception e) {
            System.out.println("Error cleaning up test admins: " + e.getMessage());
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
    void c_register() {
        // Create new admin with real data for registration test
        Admin newAdmin = AdminFactory.createAdmin("newadmin", "NewAdmin123!", "newadmin@ezelina.com", "0845555555");
        assertNotNull(newAdmin);

        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/register",
                newAdmin,
                Admin.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newadmin", response.getBody().getUsername());
        System.out.println("Registered: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_updatePassword() {
        // Update the admin's password to be properly hashed
        Admin passwordUpdate = new Admin();
        passwordUpdate.setUsername(admin.getUsername());
        passwordUpdate.setPasswordHash("SuperAdmin123!"); // Same password but will be hashed

        ResponseEntity<String> response = restTemplate.postForEntity(
                adminURL() + "/update-password",
                passwordUpdate,
                String.class
        );

        System.out.println("Password Update Response Status: " + response.getStatusCode());
        System.out.println("Password Update Response Body: " + response.getBody());
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Password updated successfully");
    }

    @Test
    @Order(5)
    void e_login() {
        // Create login request with correct credentials
        Admin loginRequest = new Admin();
        loginRequest.setUsername(admin.getUsername());
        loginRequest.setPasswordHash("SuperAdmin123!"); // Super admin password

        System.out.println("Login attempt for: " + loginRequest.getUsername());
        System.out.println("Login password: " + loginRequest.getPasswordHash());
        System.out.println("Admin in database: " + admin);

        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/login",
                loginRequest,
                Admin.class
        );

        System.out.println("Login Response Status: " + response.getStatusCode());
        System.out.println("Login Response Body: " + response.getBody());
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getUsername(), response.getBody().getUsername());
        System.out.println("Super Admin Login successful: " + response.getBody());
    }

    @Test
    @Order(6)
    void f_createAdminBySuperAdmin() {
        // Super admin creates a new admin
        Admin newAdminBySuper = AdminFactory.createAdmin("adminby_super", "AdminBySuper123!", "adminby_super@ezelina.com", "0851234567");
        assertNotNull(newAdminBySuper);

        ResponseEntity<Admin> response = restTemplate.postForEntity(
                adminURL() + "/create",
                newAdminBySuper,
                Admin.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("adminby_super", response.getBody().getUsername());
        System.out.println("Super Admin created new admin: " + response.getBody());
    }

    @Test
    @Order(7)
    void g_getAll() {
        ResponseEntity<Admin[]> response = restTemplate.getForEntity(
                adminURL() + "/all",
                Admin[].class
        );

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Admins (" + response.getBody().length + " total)");
    }
}