package ac.za.cput.factory;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.User;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.util.Helper;

public class RetailStoreFactory {

        public static RetailStore createRetailStore(String storeName, String username, String password, String email, String phoneNumber,
                                                String postalCode, String street, String city, String province, String country) {
        if (Helper.isNullOrEmpty(storeName) || Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(password) ||
                Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }
        
        String storeId = Helper.generateId();
        String storeNumber = Helper.generateId();
        
        // Create User first with all contact details
        User user = UserFactory.createUser(username, password, UserRole.STORE, email, phoneNumber, 
                                          postalCode, street, city, province, country);
        
        // Create RetailStore with reference to User
        RetailStore.Builder builder = new RetailStore.Builder();
        return builder
                .setStoreId(storeId)
                .setStoreNumber(storeNumber)
                .setStoreName(storeName)
                .setUser(user)
                .build();
    }
}