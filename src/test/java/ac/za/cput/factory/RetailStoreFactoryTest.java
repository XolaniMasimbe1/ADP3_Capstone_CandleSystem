package ac.za.cput.factory;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RetailStoreFactoryTest {

    private static User user = UserFactory.createUser(
            "KingsShop",
            "Doe", UserRole.STORE);


    private  static RetailStore retailStore = RetailStoreFactory.createRetailStore(
            "PicknPay",
            "info@picknpay.com",
            "0211234567",
            "1234",
            "Test Road",
            "Cape Town",
            "Western Cape",
            "South Africa",user
    );


    @Test
    void createRetailStore() {
        assertNotNull(retailStore);
        System.out.println("Created: " + retailStore);
    }
}