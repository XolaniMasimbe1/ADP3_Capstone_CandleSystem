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
        // Create product without image data initially
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
    void c_getImageBeforeUpload() {
        // This should return 404 - no image uploaded yet
        assertNotNull(product, "Product should not be null");

        ResponseEntity<byte[]> response = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        assertEquals(404, response.getStatusCode().value());
        System.out.println("No image found (expected) - product has no image data yet");
    }

    @Test
    @Order(4)
    void d_uploadRealImageFile() {
        try {
            // Load the actual image file from resources
            ClassPathResource imageResource = new ClassPathResource("img/img_1.png");
            assertTrue(imageResource.exists(), "Image file img_1.png should exist in resources");

            // Get file size for verification
            long fileSize = imageResource.contentLength();
            System.out.println("Original image file size: " + fileSize + " bytes");

            // Create multipart request with correct parameter name "image"
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("image", imageResource);

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
            System.out.println("Upload response: " + uploadResponse.getBody());

            // Verify the image was actually stored in DATABASE by retrieving it
            ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                    productURL() + "/image/" + product.getProductNumber(),
                    byte[].class
            );

            assertEquals(200, imageResponse.getStatusCode().value());
            assertNotNull(imageResponse.getBody());

            // The image should be stored in database, so size should match
            assertEquals(fileSize, imageResponse.getBody().length);
            System.out.println(" Retrieved image size from database: " + imageResponse.getBody().length + " bytes");
            System.out.println("REAL IMAGE FILE successfully uploaded and stored in DATABASE!");

            // Verify the product in database has the image data
            ResponseEntity<Product> productResponse = restTemplate.getForEntity(
                    productURL() + "/read/" + product.getProductNumber(),
                    Product.class
            );

            assertNotNull(productResponse.getBody());
            assertNotNull(productResponse.getBody().getImageData());
            assertEquals(fileSize, productResponse.getBody().getImageData().length);
            System.out.println("Image data verified in product entity stored in database");

        } catch (IOException e) {
            fail("Failed to load image file: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    void e_getImageAfterUpload() {
        // This should return 200 - image exists now
        assertNotNull(product, "Product should not be null");

        ResponseEntity<byte[]> response = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("Image retrieved successfully, size: " + response.getBody().length + " bytes");

        // Verify it's actually an image by checking magic numbers
        byte[] imageData = response.getBody();
        if (imageData.length >= 4) {
            // Check for PNG signature
            if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50 &&
                    imageData[2] == (byte) 0x4E && imageData[3] == (byte) 0x47) {
                System.out.println("Image type: PNG");
            }
            // Check for JPEG signature
            else if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8) {
                System.out.println(" Image type: JPEG");
            }
        }
    }

    @Test
    @Order(6)
    void f_updateProductWithoutTouchingImage() {
        assertNotNull(product, "Product should not be null");

        Product updated = new Product.Builder()
                .copy(product)
                .setPrice(75.00) // Only change price, image data should remain
                .setStockQuantity(150) // Change stock quantity
                .build();

        restTemplate.put(productURL() + "/update", updated);
        product = updated;

        // Verify the update worked
        ResponseEntity<Product> response = restTemplate.getForEntity(
                productURL() + "/read/" + product.getProductNumber(),
                Product.class
        );

        assertNotNull(response.getBody());
        assertEquals(75.00, response.getBody().getPrice());
        assertEquals(150, response.getBody().getStockQuantity());
        System.out.println("Product updated successfully: " + response.getBody());

        // Verify image still exists after update
        ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        assertEquals(200, imageResponse.getStatusCode().value());
        assertNotNull(imageResponse.getBody());
        assertTrue(imageResponse.getBody().length > 0);
        System.out.println("Image preserved after update, size: " + imageResponse.getBody().length + " bytes");
    }

    @Test
    @Order(7)
    void g_deleteImage() {
        assertNotNull(product, "Product should not be null");

        ResponseEntity<String> response = restTemplate.exchange(
                productURL() + "/image/" + product.getProductNumber(),
                HttpMethod.DELETE,
                null,
                String.class
        );

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Image deleted successfully from database", response.getBody());
        System.out.println(" " + response.getBody());

        // Verify image is deleted by trying to get it
        ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        assertEquals(404, imageResponse.getStatusCode().value());
        System.out.println("Image deletion verified - 404 returned when trying to retrieve deleted image");

        // Verify product still exists but has no image data
        ResponseEntity<Product> productResponse = restTemplate.getForEntity(
                productURL() + "/read/" + product.getProductNumber(),
                Product.class
        );

        assertNotNull(productResponse.getBody());
        assertNull(productResponse.getBody().getImageData());
        System.out.println("Product entity verified to have null image data after deletion");
    }

    @Test
    @Order(8)
    void h_getImageAfterDeletion() {
        // This should return 404 - image was deleted
        assertNotNull(product, "Product should not be null");

        ResponseEntity<byte[]> response = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        assertEquals(404, response.getStatusCode().value());
        System.out.println("No image found (expected) - image was deleted in previous test");
    }

    @Test
    @Order(9)
    void i_getAll() {
        // Get all products - should include the one we created, updated, and manipulated images for
        assertNotNull(product, "Product should not be null");

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
                // Our product should have no image data since we deleted it
                assertNull(p.getImageData(), "Our product should have null image data after deletion");
                break;
            }
        }
        assertTrue(foundOurProduct, "Our created product should be in the getAll results");

        System.out.println(" All Products (" + response.getBody().length + " total):");
        for (Product p : response.getBody()) {
            System.out.println("  - " + p.getProductNumber() + ": " + p.getName() +
                    " (Image: " + (p.getImageData() != null ? p.getImageData().length + " bytes" : "null") + ")");
        }
    }

    @Test
    @Order(10)
    void j_uploadAnotherImageAfterDeletion() {
        // Test uploading a different image after deletion
        ClassPathResource imageResource = new ClassPathResource("img/img_1.png"); // or use a different image
        assertTrue(imageResource.exists(), "Image file should exist in resources");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", imageResource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> uploadResponse = restTemplate.exchange(
                productURL() + "/upload-image/" + product.getProductNumber(),
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        assertEquals(200, uploadResponse.getStatusCode().value());
        System.out.println("Second image upload successful: " + uploadResponse.getBody());

        // Verify the new image exists
        ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(
                productURL() + "/image/" + product.getProductNumber(),
                byte[].class
        );

        assertEquals(200, imageResponse.getStatusCode().value());
        assertNotNull(imageResponse.getBody());
        System.out.println("Second image verified, size: " + imageResponse.getBody().length + " bytes");

    }
}