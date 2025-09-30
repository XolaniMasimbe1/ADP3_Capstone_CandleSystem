package ac.za.cput.factory;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Address;
import ac.za.cput.domain.ContactPerson;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.util.Helper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


public class RetailStoreFactory {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static RetailStore createRetailStore(String storeName, String storeEmail, String password,
                                               String streetNumber, String streetName, String suburb, String city, 
                                               Province province, String postalCode, String country,
                                               String contactPerson1FirstName, String contactPerson1LastName, 
                                               String contactPerson1Email, String contactPerson1Phone,
                                               String contactPerson2FirstName, String contactPerson2LastName,
                                               String contactPerson2Email, String contactPerson2Phone) {
        if (Helper.isNullOrEmpty(storeName) || Helper.isNullOrEmpty(storeEmail) || Helper.isNullOrEmpty(password)) {
            return null;
        }

        String storeId = Helper.generateId();
        String storeNumber = Helper.generateId();
        String passwordHash = passwordEncoder.encode(password);

        // Create Store Address
        Address storeAddress = AddressFactory.createAddress(
                streetNumber, streetName, suburb, city, postalCode, province, country);

        // Create Contact persons
        List<ContactPerson> contactPersons = new ArrayList<>();
        
        // Contact Person 1
        if (!Helper.isNullOrEmpty(contactPerson1FirstName) && !Helper.isNullOrEmpty(contactPerson1LastName) &&
            !Helper.isNullOrEmpty(contactPerson1Email) && !Helper.isNullOrEmpty(contactPerson1Phone)) {
            ContactPerson contactPerson1 = ContactPersonFactory.createContactPerson(contactPerson1FirstName, contactPerson1LastName, 
                                                           contactPerson1Email, contactPerson1Phone);
            if (contactPerson1 != null) {
                contactPersons.add(contactPerson1);
            }
        }
        
        // Contact Person 2
        if (!Helper.isNullOrEmpty(contactPerson2FirstName) && !Helper.isNullOrEmpty(contactPerson2LastName) &&
            !Helper.isNullOrEmpty(contactPerson2Email) && !Helper.isNullOrEmpty(contactPerson2Phone)) {
            ContactPerson contactPerson2 = ContactPersonFactory.createContactPerson(contactPerson2FirstName, contactPerson2LastName, 
                                                           contactPerson2Email, contactPerson2Phone);
            if (contactPerson2 != null) {
                contactPersons.add(contactPerson2);
            }
        }

        // Create RetailStore with Contact relationship
        RetailStore.Builder builder = new RetailStore.Builder();
        RetailStore retailStore = builder
                .setStoreId(storeId)
                .setStoreNumber(storeNumber)
                .setStoreName(storeName)
                .setStoreEmail(storeEmail)
                .setPasswordHash(passwordHash)
                .setAddress(storeAddress)
                .setContactPersons(contactPersons)
                .build();
        
        // Ensure all contact persons have the correct relationship
        if (retailStore != null && retailStore.getContactPersons() != null) {
            for (ContactPerson contactPerson : retailStore.getContactPersons()) {
                contactPerson.setRetailStore(retailStore);
            }
        }
        
        return retailStore;
    }

}