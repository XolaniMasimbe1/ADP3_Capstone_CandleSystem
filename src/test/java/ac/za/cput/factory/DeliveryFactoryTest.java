package ac.za.cput.factory;

import ac.za.cput.domain.Delivery;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryFactoryTest {

    @Test
    void createDelivery() {

        Delivery delivery = DeliveryFactory.createDelivery("Pending");
        assertNotNull(delivery);
        System.out.println("Created Delivery: " + delivery);
    }
}