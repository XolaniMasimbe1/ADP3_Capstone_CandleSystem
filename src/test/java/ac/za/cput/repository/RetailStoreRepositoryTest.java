package ac.za.cput.repository;
/*
 * RetailStoreRepositoryTest.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 June 2025
 **/
import ac.za.cput.domain.RetailStore;
import ac.za.cput.factory.RetailStoreFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RetailStoreRepositoryTest {

    @Autowired
    private RetailStoreRepository retailStoreRepository;

    private static RetailStore retailStore;

    @BeforeAll
    static void init() {
        // Create the RetailStore once for all tests (note the fixed store number)
        retailStore = RetailStoreFactory.createRetailStore(
                "Tech haven",
                "John Deo",
                "222410817@mycput.ac.za",
                "0612773329",
                "5555", // fixed store number for consistent testing
                "123 Tech Street",
                "Cape Town",
                "Western Cape",
                "South Africa"
        );
    }

    @BeforeEach
    void setUp() {
        // Ensure the store exists in the DB before each test
        if (!retailStoreRepository.existsById(retailStore.getStoreNumber())) {
            retailStoreRepository.save(retailStore);
        }
    }

    @Test
    @Order(1)
    void createRetailStore() {
        RetailStore createdRetailStore = retailStoreRepository.save(retailStore);
        assertNotNull(createdRetailStore);
        System.out.println("Created Retail Store: " + createdRetailStore);
    }

    @Test
    @Order(2)
    void readRetailStore() {
        RetailStore foundRetailStore = retailStoreRepository.findById(retailStore.getStoreNumber())
                .orElse(null);

        assertNotNull(foundRetailStore, "The retrieved RetailStore should not be null");
        System.out.println("Retrieved Retail Store: " + foundRetailStore);
    }

    @Test
    @Order(3)
    void updateRetailStore() {
        RetailStore updatedRetailStore = new RetailStore.Builder()
                .copy(retailStore)
                .setContactPerson("Jane Doe")
                .build();
        RetailStore savedRetailStore = retailStoreRepository.save(updatedRetailStore);
        assertNotNull(savedRetailStore);
        System.out.println("Updated Retail Store: " + savedRetailStore);
    }

    @Test
    @Order(4)
    @Disabled("Disabled until manual test cleanup is enabled")
    void deleteRetailStore() {
        retailStoreRepository.deleteById(retailStore.getStoreNumber());
        assertFalse(retailStoreRepository.existsById(retailStore.getStoreNumber()));
        System.out.println("Deleted Retail Store with ID: " + retailStore.getStoreNumber());
    }

    @Test
    @Order(5)
    void findById() {
        RetailStore foundRetailStore = retailStoreRepository.findById(retailStore.getStoreNumber())
                .orElse(null);
        assertNotNull(foundRetailStore);
        System.out.println("Found Retail Store by ID: " + foundRetailStore);
    }
}
