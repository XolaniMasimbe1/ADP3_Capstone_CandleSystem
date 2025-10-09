package ac.za.cput.service;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Enum.Province;
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

    private  static RetailStore retailStore = RetailStoreFactory.createRetailStore(
            "PicknPay",
            "info@picknpay.com",
            "password123",
            "123",
            "Main Street",
            "CBD",
            "Cape Town",
            Province.WESTERN_CAPE,
            "8001",
            "South Africa",
            "John",
            "Doe",
            "john@picknpay.com",
            "+27123456789",
            "Jane",
            "Smith",
            "jane@picknpay.com",
            "+27987654321"
    );


    @Test
    void create() {
        RetailStore createdStore = retailStoreService.create(retailStore);
        assertNotNull(createdStore);
        System.out.println("Created: " + createdStore);
    }

    @Test
    void read() {
        RetailStore readStore = retailStoreService.read(retailStore.getStoreId());
        assertNotNull(readStore);
        System.out.println("Read: " + readStore);
    }

    @Test
    void update() {
        String storeId = "8a1c07ef-41f8-4ceb-a420-935e743d812c";
        RetailStore updatedStore = new RetailStore.Builder()
                .setStoreId(storeId)
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