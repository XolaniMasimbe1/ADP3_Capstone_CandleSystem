package ac.za.cput.factory;

import ac.za.cput.domain.ContactDetails;
import ac.za.cput.util.Helper;

public class ContactDetailsFactory {

    public static ContactDetails createContactDetails(String email, String phoneNumber, String postalCode,
            String street, String city, String province, String country) {

        if (Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }

        ContactDetails.Builder builder = new ContactDetails.Builder();
        return builder
                .setContactId(Helper.generateId())
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setPostalCode(postalCode)
                .setStreet(street)
                .setCity(city)
                .setProvince(province)
                .setCountry(country)
                .build();
    }


}
