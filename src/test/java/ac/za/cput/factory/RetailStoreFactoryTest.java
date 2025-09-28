package ac.za.cput.factory;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Enum.Province;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetailStoreFactoryTest {

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
    void createRetailStore() {
        assertNotNull(retailStore);
        System.out.println("Created: " + retailStore);
    }
}