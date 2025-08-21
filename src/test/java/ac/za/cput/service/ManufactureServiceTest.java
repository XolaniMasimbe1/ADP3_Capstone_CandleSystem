package ac.za.cput.service;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.factory.ManufactureFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufactureServiceTest {

    @Autowired
    private ManufactureService manufactureService;

    private static Manufacture manufacture = ManufactureFactory.createManufacture("Scented Candles Inc.");

    @Test
    @Order(1)
    void create() {
        Manufacture createdManufacture = manufactureService.create(manufacture);
        assertNotNull(createdManufacture);
        System.out.println("Created Manufacture: " + createdManufacture);
    }

    @Test
    @Order(2)
    void read() {
        Manufacture readManufacture = manufactureService.read(manufacture.getManufacturerNumber());
        assertNotNull(readManufacture);
        System.out.println("Read Manufacture: " + readManufacture);
    }

    @Test
    @Order(3)
    void update() {
        Manufacture updatedManufacture = new Manufacture.Builder()
                .setManufacturerNumber(manufacture.getManufacturerNumber())
                .setManufacturerName("Blues Candles Co.")
                .build();

        Manufacture result = manufactureService.update(updatedManufacture);
        assertNotNull(result);
        System.out.println("Updated Manufacture: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        assertFalse(manufactureService.getAll().isEmpty(), "Manufacture list should not be empty");
        System.out.println("All Manufactures: " + manufactureService.getAll());
    }
}