package ac.za.cput.factory;

import ac.za.cput.domain.RetailStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
/*
 * RetailFactoryTest.java
 * Test for Retail
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 June 2025
 **/
class RetailStoreFactoryTest {

    private  static RetailStore retailStore = RetailStoreFactory.createRetailStore("Tech haven","John Deo"
    ,"222410817@mycput.ac.za","0612773329","5555","123 Tech Street",
            "Cape Town","Western Cape","South Africa");

    @Test
    void createRetailStore() {
        assertNotNull(retailStore);
        System.out.println(retailStore);
    }
}