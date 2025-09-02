package ac.za.cput.factory;

import ac.za.cput.domain.RetailStore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetailStoreFactoryTest {

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
    void createRetailStore() {
        assertNotNull(retailStore);
        System.out.println("Created: " + retailStore);
    }
}