package ac.za.cput.factory;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Address;
import ac.za.cput.domain.Contact;
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
                streetNumber, streetName, suburb, city, province.toString(), postalCode, country);

        // Create Contact persons
        List<Contact> contacts = new ArrayList<>();
        
        // Contact Person 1
        if (!Helper.isNullOrEmpty(contactPerson1FirstName) && !Helper.isNullOrEmpty(contactPerson1LastName) &&
            !Helper.isNullOrEmpty(contactPerson1Email) && !Helper.isNullOrEmpty(contactPerson1Phone)) {
            Contact contact1 = ContactFactory.createContact(contactPerson1FirstName, contactPerson1LastName, 
                                                           contactPerson1Email, contactPerson1Phone);
            if (contact1 != null) {
                contacts.add(contact1);
            }
        }
        
        // Contact Person 2
        if (!Helper.isNullOrEmpty(contactPerson2FirstName) && !Helper.isNullOrEmpty(contactPerson2LastName) &&
            !Helper.isNullOrEmpty(contactPerson2Email) && !Helper.isNullOrEmpty(contactPerson2Phone)) {
            Contact contact2 = ContactFactory.createContact(contactPerson2FirstName, contactPerson2LastName, 
                                                           contactPerson2Email, contactPerson2Phone);
            if (contact2 != null) {
                contacts.add(contact2);
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
                .setContacts(contacts)
                .build();
        
        // Ensure all contacts have the correct relationship
        if (retailStore != null && retailStore.getContacts() != null) {
            for (Contact contact : retailStore.getContacts()) {
                contact.setRetailStore(retailStore);
            }
        }
        
        return retailStore;
    }

}