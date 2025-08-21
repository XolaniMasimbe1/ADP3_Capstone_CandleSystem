package ac.za.cput.controller;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;
import ac.za.cput.factory.UserFactory;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    private static final String BASE_URL = "/user";
    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("vsm","12we4",UserRole.STORE);
    }

    @Test
    @Order(1)
    void create() {
        ResponseEntity<User> response = restTemplate.postForEntity(
                BASE_URL + "/create",
                user,
                User.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUsername(), response.getBody().getUsername());
        System.out.println("Created User: " + response.getBody());
    }

    @Test
    @Order(2)
    void read() {
        ResponseEntity<User> response = restTemplate.getForEntity(
                BASE_URL + "/read/" + user.getUserId(),
                User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(user.getUserId(), response.getBody().getUserId());
        System.out.println("Read User: " + response.getBody());
    }

    @Test
    @Order(3)
    void update() {
        User updated = new User.Builder()
                .setUserId(user.getUserId())
                .setUsername("xolani_updated")
                .setPasswordHash(user.getPasswordHash())
                .setRole(user.getRole())
                .build();

        // FIX 2: Use an exchange method with HttpMethod.PUT for updates.
        ResponseEntity<User> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                User.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("xolani_updated", response.getBody().getUsername());
        System.out.println("Updated User: " + response.getBody());

        user = response.getBody(); // keep updated reference
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                BASE_URL + "/getAll",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertTrue(response.getBody().stream().anyMatch(u -> u.getUserId().equals(user.getUserId())));
        System.out.println("All Users: " + response.getBody());
    }

}