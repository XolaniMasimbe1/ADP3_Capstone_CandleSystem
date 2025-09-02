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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private ManufactureService manufactureService;

    private static Product product;
    private Manufacture manufacture;

    private String baseURL() {
        return "http://localhost:" + port + "/CandleSystem";
    }
    
    private String productURL() {
        return baseURL() + "/product";
    }

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
                productURL() + "/create",
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
                productURL() + "/read/" + product.getProductNumber(),
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals(product.getProductNumber(), response.getBody().getProductNumber());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        // Update the SAME product that was created in the first test
        assertNotNull(product, "Product should not be null - run create test first");
        
        Product updated = new Product.Builder()
                .copy(product)
                .setPrice(60.00)
                .build();

        restTemplate.put(
                productURL() + "/update",
                updated
        );

        // Update our reference to the updated product
        product = updated;

        ResponseEntity<Product> response = restTemplate.getForEntity(
                productURL() + "/read/" + product.getProductNumber(),
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals(60.00, response.getBody().getPrice());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_getAll() {
        // Get all products - should include the one we created and updated
        assertNotNull(product, "Product should not be null - run create test first");
        
        ResponseEntity<Product[]> response = restTemplate.getForEntity(
                productURL() + "/all",
                Product[].class
        );

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        
        // Verify our created product is in the list
        boolean foundOurProduct = false;
        for (Product p : response.getBody()) {
            if (p.getProductNumber().equals(product.getProductNumber())) {
                foundOurProduct = true;
                break;
            }
        }
        assertTrue(foundOurProduct, "Our created product should be in the getAll results");
        
        System.out.println("All Products (" + response.getBody().length + " total):");
        for (Product p : response.getBody()) {
            System.out.println(p);
        }
    }
}