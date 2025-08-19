package ac.za.cput.factory;

import ac.za.cput.domain.Manufacture;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManufactureFactoryTest {

    @Test
    void createManufacture() {
        Manufacture manufacture = ManufactureFactory.createManufacture("Scented Candles Inc.");

        assertNotNull(manufacture);
        System.out.println("Created Manufacture: " + manufacture);
    }
}