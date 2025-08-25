package ac.za.cput.factory;

import ac.za.cput.domain.ContactDetails;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.User;
import ac.za.cput.util.Helper;

public class RetailStoreFactory {
    public static RetailStore createRetailStore(String storeName, String email, String phoneNumber, String postalCode, String street, String city, String province, String country, User user) {
        if (Helper.isNullOrEmpty(storeName)) {
            System.err.println("Store name is null or empty");
            return null;
        }
        if (!Helper.isValidEmail(email)) {
            System.err.println("Invalid email: " + email);
            return null;
        }
        if (!Helper.isValidPhoneNumber(phoneNumber)) {
            System.err.println("Invalid phone: " + phoneNumber);
            return null;
        }
        String storeNumber = Helper.generateId();
        ContactDetails contactDetails = new ContactDetails(email, phoneNumber, postalCode, city, country, province, street);
        return new RetailStore.Builder()
                .setStoreNumber(storeNumber)
                .setStoreName(storeName)
                .setContactDetails(contactDetails)
                .setUser(user)
                .build();
    }

}