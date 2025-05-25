package ac.za.cput.factory;

import ac.za.cput.domain.ContactDetails;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.util.Helper;
/*
 * RetailFactory.java
 * Factoru for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 May 2025
 **/
public class RetailStoreFactory {
    public static RetailStore createRetailStore( String storeName, String contactPerson,
                                                String email, String phoneNumber, String postalCode
                     , String street, String city, String province, String country) {
        if (Helper.isNullOrEmpty(storeName) || Helper.isNullOrEmpty(contactPerson) ||
                !Helper.isValidEmail(email) || !Helper.isValidPhoneNumber(phoneNumber) ||
                Helper.isNullOrEmpty(postalCode) || Helper.isNullOrEmpty(street) ||
                Helper.isNullOrEmpty(city) || Helper.isNullOrEmpty(province) || Helper.isNullOrEmpty(country)) {
            return null; // Invalid input
        }
        String storeId = Helper.generateId();
        ContactDetails contactDetails = new ContactDetails(email, phoneNumber, postalCode, street, city, province, country);
        return new RetailStore.Builder()
                .setStoreNumber(storeId)
                .setStoreName(storeName)
                .setContactPerson(contactPerson)
                .setContactDetails(contactDetails)
                .build();
    }
}
