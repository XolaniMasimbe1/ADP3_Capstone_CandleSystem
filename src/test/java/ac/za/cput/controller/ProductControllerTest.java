package ac.za.cput.controller;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.domain.Product;
import ac.za.cput.factory.ManufactureFactory;
import ac.za.cput.factory.ProductFactory;
import ac.za.cput.service.ManufactureService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ManufactureService manufactureService;

    private static Product product;
    private Manufacture manufacture;

    private final String BASE_URL = "http://localhost:8080/CandleSystem";
    private final String PRODUCT_URL = BASE_URL + "/product";

    @BeforeAll
    void setUp() {
        manufacture = manufactureService.create(ManufactureFactory.createManufacture("Candle Manufacturer"));
        assertNotNull(manufacture);
        product = ProductFactory.createProduct("Beeswax Candle ", 50.00, 100, "Scent", "Color", "Large", manufacture);
        assertNotNull(product);
    }

    @Test
    @Order(1)
    void a_create() {
        ResponseEntity<Product> response = restTemplate.postForEntity(
                PRODUCT_URL + "/create",
                product,
                Product.class
        );

        assertNotNull(response.getBody());
        product = response.getBody();
        assertEquals(product.getProductNumber(), response.getBody().getProductNumber());
        System.out.println("Created: " + product);
    }

    @Test
    @Order(2)
    void b_read() {
        ResponseEntity<Product> response = restTemplate.getForEntity(
                PRODUCT_URL + "/read/" + product.getProductNumber(),
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals(product.getProductNumber(), response.getBody().getProductNumber());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Product updated = new Product.Builder()
                .copy(product)
                .setPrice(60.00)
                .build();

        restTemplate.put(
                PRODUCT_URL + "/update",
                updated
        );

        ResponseEntity<Product> response = restTemplate.getForEntity(
                PRODUCT_URL + "/read/" + updated.getProductNumber(),
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals(60.00, response.getBody().getPrice());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_getAll() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(
                PRODUCT_URL + "/all",
                Product[].class
        );

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Products: ");
        for (Product p : response.getBody()) {
            System.out.println(p);
        }
    }
}