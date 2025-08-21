package ac.za.cput.factory;

import ac.za.cput.domain.Manufacture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManufactureFactoryTest {

    private static Manufacture manufacture = ManufactureFactory.createManufacture("Scented Candles Inc.");

    @Test
    void createManufacture() {
        assertNotNull(manufacture);
        System.out.println("Created Manufacture: " + manufacture);
    }
}