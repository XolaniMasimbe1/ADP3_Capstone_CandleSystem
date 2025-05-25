package ac.za.cput.service;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.factory.RetailStoreFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RetailStoreServiceTest {
    @Autowired
    private RetailStoreService retailStoreService;
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

        if (retailStoreService.read(retailStore.getStoreNumber()) == null) {
            retailStoreService.create(retailStore);
        }
    }
    @Test
    void create() {
        RetailStore createdRetailStore = retailStoreService.create(retailStore);
        assertNotNull(createdRetailStore);
        System.out.println("Created Retail Store: " + createdRetailStore);
    }

    @Test
    void read() {
        RetailStore foundRetailStore = retailStoreService.read(retailStore.getStoreNumber());
        assertNotNull(foundRetailStore);
        System.out.println("Retrieved Retail Store: " + foundRetailStore);
    }

    @Test
    void update() {
        RetailStore updatedRetailStore = retailStoreService.update(retailStore);
        assertNotNull(updatedRetailStore);
        System.out.println("Updated Retail Store: " + updatedRetailStore);
    }

    @Test
    void findByStoreNumber() {
        String storeNumber = retailStore.getStoreNumber();
        RetailStore foundRetailStore = retailStoreService.findByStoreNumber(storeNumber)
                .orElse(null);
        assertNotNull(foundRetailStore, "The retrieved RetailStore should not be null");
        System.out.println("Found Retail Store by Store Number: " + foundRetailStore);
    }
}