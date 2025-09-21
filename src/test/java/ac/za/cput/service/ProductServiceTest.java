package ac.za.cput.service;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.domain.Product;
import ac.za.cput.factory.ManufactureFactory;
import ac.za.cput.factory.ProductFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private static Manufacture manufacture = ManufactureFactory.createManufacture("Suited Candles Inc.");
    private static byte[] testImageData = "test-image-data-for-service".getBytes();

    private static Product product = ProductFactory.createProduct(
            "Candle",
            700.00,
            1999,
            "Grape",
            "Yellow",
            "Large",
            testImageData,
            manufacture);
    @Test
    void create() {
        Product createdProduct = productService.create(product);
        assertNotNull(createdProduct);
        System.out.println("Created: " + createdProduct);
    }

    @Test
    void read() {
        Product readProduct = productService.read(product.getProductNumber());
        assertNotNull(readProduct);
        System.out.println("Read: " + readProduct);
    }

    @Test
    void update() {
        byte[] newImageData = "updated-image-data".getBytes();
        Product updatedProduct = new Product.Builder()
                .copy(product)
                .setScent("Lavender")
                .setSize("Medium")
                .setImageData(newImageData)
                .build();
        assertNotNull(productService.update(updatedProduct));
        System.out.println("Updated: " + updatedProduct);
    }

    @Test
    void getAll() {
        assertFalse(productService.getAll().isEmpty(), "Product list should not be empty");
        System.out.println("All Products: " + productService.getAll());
    }
}