package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.factory.AdminFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/CandleSystem/admin";
    private static Admin admin;

    @BeforeAll
    static void setUp() {
        admin = AdminFactory.createAdmin("admin_test", "password123", "admin@example.com", "+1234567890");
    }

    @Test
    @Order(1)
    void create() {
        ResponseEntity<Admin> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                admin,
                Admin.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        admin = response.getBody();
        System.out.println("Created Admin: " + admin);
    }

    @Test
    @Order(2)
    void read() {
        String url = BASE_URL + "/read/{adminId}";
        ResponseEntity<Admin> response = restTemplate.getForEntity(url, Admin.class, admin.getAdminId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(admin.getAdminId(), response.getBody().getAdminId());

        System.out.println("Read Admin: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        Admin updated = new Admin.Builder()
                .setAdminId(admin.getAdminId())
                .setUsername("admin_updated")
                .setPasswordHash(admin.getPasswordHash())
                .setEmail("admin.updated@example.com")
                .setPhoneNumber("+0987654321")
                .build();

        System.out.println("Updating admin with ID: " + updated.getAdminId());

        HttpEntity<Admin> entity = new HttpEntity<>(updated);

        ResponseEntity<Admin> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                entity,
                Admin.class
        );

        assertNotNull(response.getBody());
        admin = response.getBody();
        System.out.println("Updated Admin: " + admin);
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<List<Admin>> response = restTemplate.exchange(
                BASE_URL + "/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertTrue(response.getBody().stream().anyMatch(a -> a.getAdminId().equals(admin.getAdminId())));

        System.out.println("All Admins: " + response.getBody());
    }

  /*  @Test
    @Order(5)
    void delete() {
        HttpEntity<Void> entity = new HttpEntity<>(null);

        ResponseEntity<Void> response = restTemplate.exchange(
                BASE_URL + "/delete/{adminId}",
                HttpMethod.DELETE,
                entity,
                Void.class,
                admin.getAdminId()
        );

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        System.out.println("Deleted Admin with ID: " + admin.getAdminId());
    }*/
}
