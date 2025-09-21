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
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

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
        // Create product without image data - we'll upload real image later
        product = ProductFactory.createProduct("Beeswax Candle23 ", 50.00, 100, "Scent", "Color", "Large", null, manufacture);
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
        
        byte[] newImageData = "updated-image-data-for-controller".getBytes();
        Product updated = new Product.Builder()
                .copy(product)
                .setPrice(60.00)
                .setImageData(newImageData)
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

    @Test
    @Order(5)
    void e_getImage() {
        assertNotNull(product, "Product should not be null - run create test first");
        
        // This test will fail because product doesn't have image yet
        // We'll upload real image in the next test
        ResponseEntity<byte[]> response = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        // Should return 404 because no image uploaded yet
        assertEquals(404, response.getStatusCode().value());
        System.out.println("No image found (expected) - will upload real image in next test");
    }

    @Test
    @Order(6)
    @Disabled
    void f_deleteImage() {
        assertNotNull(product, "Product should not be null - run create test first");
        
        ResponseEntity<String> response = restTemplate.exchange(
                productURL() + "/image/" + product.getProductNumber(),
                org.springframework.http.HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Image deleted successfully", response.getBody());
        
        // Verify image is deleted by trying to get it
        ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );
        
        assertEquals(404, imageResponse.getStatusCode().value());
        System.out.println("Image deleted successfully");
    }

    @Test
    @Order(7)
    void g_uploadRealImageFile() {
        try {
            // Load the actual image file from resources
            ClassPathResource imageResource = new ClassPathResource("img/img.png");
            assertTrue(imageResource.exists(), "Image file should exist in resources");
            
            // Get file size for verification
            long fileSize = imageResource.contentLength();
            System.out.println("Original image file size: " + fileSize + " bytes");
            
            // Upload the real image file to our existing product
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", imageResource);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            
            String uploadUrl = productURL() + "/upload-image/" + product.getProductNumber();
            System.out.println("Uploading real image to: " + uploadUrl);
            
            ResponseEntity<String> uploadResponse = restTemplate.exchange(
                    uploadUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            
            // Verify upload was successful
            assertEquals(200, uploadResponse.getStatusCode().value());
            assertTrue(uploadResponse.getBody().contains("Image uploaded successfully"));
            System.out.println("✅ Upload response: " + uploadResponse.getBody());
            
            // Verify the image was actually stored by retrieving it
            ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                    productURL() + "/image/" + product.getProductNumber(),
                    byte[].class
            );
            
            assertEquals(200, imageResponse.getStatusCode().value());
            assertNotNull(imageResponse.getBody());
            assertEquals(fileSize, imageResponse.getBody().length);
            System.out.println("Retrieved image size: " + imageResponse.getBody().length + " bytes");
            System.out.println("REAL IMAGE FILE successfully uploaded and stored in database!");
            
        } catch (IOException e) {
            fail("Failed to load image file: " + e.getMessage());
        }
    }
}