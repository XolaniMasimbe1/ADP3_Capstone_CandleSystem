package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;
import ac.za.cput.factory.UserFactory;
import ac.za.cput.factory.AdminFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/CandleSystem/auth";
    private static Admin adminUser;
    private static User storeUser;

    @BeforeAll
    static void setUp() {
        adminUser = AdminFactory.createAdmin("admin_test", "password123", "admin@example.com", "+1234567890");
        storeUser = UserFactory.createUser("store_test", "password123", UserRole.STORE);
    }

    @Test
    @Order(1)
    void registerAdmin() {
        Map<String, String> adminPayload = new HashMap<>();
        adminPayload.put("username", "admin_new");
        adminPayload.put("password", "password123");
        adminPayload.put("email", "admin_new@example.com");
        adminPayload.put("phoneNumber", "+0987654321");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                BASE_URL + "/register/admin",
                adminPayload,
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Admin registration successful", response.getBody().get("message"));
        assertEquals("ADMIN", response.getBody().get("type"));

        System.out.println("Registered Admin: " + response.getBody());
    }

    @Test
    @Order(2)
    void registerStore() {
        Map<String, String> storePayload = new HashMap<>();
        storePayload.put("username", "store_new");
        storePayload.put("password", "password123");
        storePayload.put("storeName", "Test Store");
        storePayload.put("email", "store@example.com");
        storePayload.put("phoneNumber", "+1111111111");
        storePayload.put("postalCode", "12345");
        storePayload.put("street", "123 Test St");
        storePayload.put("city", "Test City");
        storePayload.put("province", "Test Province");
        storePayload.put("country", "Test Country");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                BASE_URL + "/register/store",
                storePayload,
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Registration successful", response.getBody().get("message"));

        System.out.println("Registered Store: " + response.getBody());
    }

    @Test
    @Order(3)
    void loginAsAdmin() {
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", "admin_new");
        loginPayload.put("password", "password123");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                BASE_URL + "/login",
                loginPayload,
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Login successful", response.getBody().get("message"));
        assertEquals("ADMIN", response.getBody().get("type"));
        assertEquals("/admin/dashboard", response.getBody().get("redirectTo"));

        System.out.println("Admin Login Response: " + response.getBody());
    }

    @Test
    @Order(4)
    void loginAsStore() {
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", "store_new");
        loginPayload.put("password", "password123");

        ResponseEntity<Map> response = restTemplate.postForEntity(
                BASE_URL + "/login",
                loginPayload,
                Map.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Login successful", response.getBody().get("message"));
        assertEquals("STORE", response.getBody().get("role"));
        assertEquals("/store/dashboard", response.getBody().get("redirectTo"));

        System.out.println("Store Login Response: " + response.getBody());
    }

    @Test
    @Order(5)
    void loginWithInvalidCredentials() {
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", "nonexistent");
        loginPayload.put("password", "wrongpassword");

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL + "/login",
                loginPayload,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid username or password", response.getBody());

        System.out.println("Invalid Login Response: " + response.getBody());
    }

    @Test
    @Order(6)
    void registerAdminWithExistingUsername() {
        Map<String, String> adminPayload = new HashMap<>();
        adminPayload.put("username", "admin_new"); // Same username as before
        adminPayload.put("password", "password123");
        adminPayload.put("email", "different@example.com");
        adminPayload.put("phoneNumber", "+5555555555");

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL + "/register/admin",
                adminPayload,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username already exists", response.getBody());

        System.out.println("Duplicate Username Response: " + response.getBody());
    }

    @Test
    @Order(7)
    void registerAdminWithExistingEmail() {
        Map<String, String> adminPayload = new HashMap<>();
        adminPayload.put("username", "admin_different");
        adminPayload.put("password", "password123");
        adminPayload.put("email", "admin_new@example.com"); // Same email as before
        adminPayload.put("phoneNumber", "+5555555555");

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL + "/register/admin",
                adminPayload,
                String.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody());

        System.out.println("Duplicate Email Response: " + response.getBody());
    }
}
