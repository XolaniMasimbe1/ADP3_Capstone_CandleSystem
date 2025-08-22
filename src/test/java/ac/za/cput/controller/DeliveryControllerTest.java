package ac.za.cput.controller;

import ac.za.cput.domain.Delivery;
import ac.za.cput.factory.DeliveryFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeliveryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String DELIVERY_URL = "/delivery";
    private static Delivery delivery;

    @BeforeAll
    static void setUp() {
        delivery = DeliveryFactory.createDelivery("Cancelled");
    }

    @Test
    void create() {
        ResponseEntity<Delivery> response = restTemplate.postForEntity(DELIVERY_URL + "/create", delivery, Delivery.class);
        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void read() {
        ResponseEntity<Delivery> response = restTemplate.getForEntity(
                DELIVERY_URL + "/read/" + delivery.getDeliveryNumber(), Delivery.class);
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void update() {
        Delivery updated = new Delivery.Builder().copy(delivery).setStatus("Delivered").build();
        restTemplate.postForEntity(DELIVERY_URL + "/update", updated, Delivery.class);
        ResponseEntity<Delivery> response = restTemplate.getForEntity(
                DELIVERY_URL + "/read/" + updated.getDeliveryNumber(), Delivery.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void getAll() {
        ResponseEntity<Delivery[]> response = restTemplate.getForEntity(DELIVERY_URL + "/all", Delivery[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Deliveries: ");
        for (Delivery d : response.getBody()) {
            System.out.println(d);
        }
    }
}