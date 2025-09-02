package ac.za.cput.service;

import ac.za.cput.domain.ContactDetails;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.factory.RetailStoreFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RetailStoreServiceTest {

    @Autowired
    private RetailStoreService retailStoreService;
    @Autowired
    private UserService userService;

    private  static RetailStore retailStore = RetailStoreFactory.createRetailStore(
            "PicknPay",
            "picknpay_user",
            "password123",
            "info@picknpay.com",
            "0211234567",
            "8001",
            "123 Main Street",
            "Cape Town",
            "Western Cape",
            "South Africa"
    );


    @Test
    void create() {
        RetailStore createdStore = retailStoreService.create(retailStore);
        assertNotNull(createdStore);
        System.out.println("Created: " + createdStore);
    }

    @Test
    void read() {
        RetailStore readStore = retailStoreService.read(retailStore.getStoreNumber());
        assertNotNull(readStore);
        System.out.println("Read: " + readStore);
    }

    @Test
    void update() {
        String storeNumber = "8a1c07ef-41f8-4ceb-a420-935e743d812c";
        RetailStore updatedStore = new RetailStore.Builder()
                .setStoreNumber(storeNumber)
                .setStoreName("Updated Store")
                .build();
        RetailStore result = retailStoreService.update(updatedStore);
        assertNotNull(result);
        System.out.println("Updated: " + result);
    }

    @Test
    void getAll() {
        var stores = retailStoreService.getAll();
        assertFalse(stores.isEmpty());
        System.out.println("All Stores: " + stores);
    }


}