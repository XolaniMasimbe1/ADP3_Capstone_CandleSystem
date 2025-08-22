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
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/CandleSystem/user";
    private static User user;

    @BeforeAll
    static void setUp() {
        user = UserFactory.createUser("v2ebevvz76u3sm", "1e83t77372e9we4", UserRole.STORE);
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

        user = response.getBody();


        System.out.println("Created User: " + user);
    }

    @Test
    @Order(2)
    void read() {
        // Either option 1:
        String url = BASE_URL + "/read/{userId}";
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class, user.getUserId());

        // Or option 2:
        // String url = BASE_URL + "/read/" + user.getUserId();
        // ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

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
                .setUsername("xolani_updated912")
                .setPasswordHash(user.getPasswordHash())
                .setRole(user.getRole())
                .build();


        System.out.println("Updating user with ID: " + updated.getUserId());

        HttpEntity<User> entity = new HttpEntity<>(updated);

        ResponseEntity<User> response = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                entity,
                User.class
        );


        assertNotNull(response.getBody());
        user = response.getBody();
        System.out.println("Updated User: " + user);
    }

    @Test
    @Order(4)
    void getAll() {
        ResponseEntity<List<User>> response = restTemplate.exchange(
                BASE_URL + "/getAll",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertTrue(response.getBody().stream().anyMatch(u -> u.getUserId().equals(user.getUserId())));

        System.out.println("All Users: " + response.getBody());
    }


}
