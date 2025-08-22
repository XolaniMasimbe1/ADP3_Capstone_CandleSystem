package ac.za.cput.controller;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.domain.Product;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.User;
import ac.za.cput.factory.ManufactureFactory;
import ac.za.cput.factory.ProductFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {



    private static final String PRODUCT_URL = "/product";
    private static final String MANUFACTURE_URL = "/manufacture";
    private static Product product;
    private static Manufacture manufacture;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    static void setUp() {
       manufacture = ManufactureFactory.createManufacture("Scented Candles Inc.");

       product = ProductFactory.createProduct(
                "Candle001",
                700.00,
                1999,
                "Grape",
                "Purple",
                "Large",
                manufacture);
    }

    @Test
    void create() {
        // Create manufacture
        restTemplate.postForEntity(MANUFACTURE_URL + "/create", manufacture, Manufacture.class);

        // Create product
        ResponseEntity<Product> response = restTemplate.postForEntity(PRODUCT_URL + "/create", product, Product.class);


        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void read() {
        // Read product by product number
        var response = restTemplate.getForEntity(PRODUCT_URL + "/read/" + product.getProductNumber(), Product.class);

        // Check for HTTP status and response body.
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void update() {

        Product updated = new Product.Builder()
                .copy(product)
                .setName("PicknPay Updated")
                .build();


        ResponseEntity<Product> response = restTemplate.exchange(
                PRODUCT_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updated),
                Product.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PicknPay Updated", response.getBody().getName());
        System.out.println("Updated: " + response.getBody());
    }


    @Test
    void getAll() {

        ResponseEntity<Product[]> response = restTemplate.getForEntity(PRODUCT_URL + "/all", Product[].class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Products: " + response.getBody().length);
    }
}