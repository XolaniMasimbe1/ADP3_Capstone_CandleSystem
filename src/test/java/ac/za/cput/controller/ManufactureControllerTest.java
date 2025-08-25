package ac.za.cput.controller;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.factory.ManufactureFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufactureControllerTest {

    private static final String MANUFACTURE_URL = "/manufacture";

    @Autowired
    private TestRestTemplate restTemplate;
    private static Manufacture manufacture;

    @BeforeAll
    static void setUp() {
        manufacture = ManufactureFactory.createManufacture("Whole Candles Inc.");
    }
    @Test
    void create() {
        var response = restTemplate.postForEntity(MANUFACTURE_URL + "/create", manufacture, Manufacture.class);
        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void read() {
        var response = restTemplate.getForEntity(MANUFACTURE_URL + "/read/" + manufacture.getManufacturerNumber(), Manufacture.class);
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void update() {
        Manufacture updated = new Manufacture.Builder().copy(manufacture)
                .setManufacturerName("Candle World sales").
                build();
        var response = restTemplate.postForEntity(MANUFACTURE_URL + "/update", updated, Manufacture.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    void getAll() {
        ResponseEntity<Manufacture[]> response = restTemplate.getForEntity(MANUFACTURE_URL + "/all", Manufacture[].class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Products: " + response.getBody().length);
    }
}