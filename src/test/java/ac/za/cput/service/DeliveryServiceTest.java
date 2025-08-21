package ac.za.cput.service;

import ac.za.cput.domain.Delivery;
import ac.za.cput.factory.DeliveryFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeliveryServiceTest {

    @Autowired
    private DeliveryService deliveryService;

    private  static Delivery delivery = DeliveryFactory.createDelivery("Pending");


    @Test
    @Order(1)
    void create() {
        Delivery createdDelivery = deliveryService.create(delivery);
        assertNotNull(createdDelivery);
        System.out.println("Created Delivery: " + createdDelivery);
    }

    @Test
    @Order(2)
    void read() {
        Delivery readDelivery = deliveryService.read(delivery.getDeliveryNumber());
        assertNotNull(readDelivery);
        System.out.println("Read Delivery: " + readDelivery);
    }

    @Test
    @Order(3)
    void update() {
        Delivery updatedDelivery = new Delivery.Builder()
                .setDeliveryNumber(delivery.getDeliveryNumber())
                .setStatus("CANCELLED")
                .build();

        Delivery result = deliveryService.update(updatedDelivery);
        assertNotNull(result);
        System.out.println("Updated Delivery: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        List<Delivery> deliveries = deliveryService.getAll();
        System.out.println("All Deliveries: " + deliveryService.getAll());
    }
}