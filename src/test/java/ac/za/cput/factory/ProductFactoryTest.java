package ac.za.cput.factory;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.domain.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductFactoryTest {

   private static Manufacture manufacture = ManufactureFactory.createManufacture("Scented Candles Inc.");

   private static Product product = ProductFactory.createProduct(
           "Candle001",
           700.00,
           1999,
           "Grape",
           "Purple",
           "Large",
           manufacture);

    @Test
    void createProduct() {
        assertNotNull(product);
        System.out.println(product);
    }
}