package ac.za.cput.factory;

import ac.za.cput.domain.ContactPerson;
import ac.za.cput.util.Helper;

public class ContactPersonFactory {
    
    public static ContactPerson createContactPerson(String firstName, String lastName, 
                                                   String emailAddress, String cellPhoneNumber) {
        if (Helper.isNullOrEmpty(firstName) || Helper.isNullOrEmpty(lastName) || 
            Helper.isNullOrEmpty(emailAddress) || Helper.isNullOrEmpty(cellPhoneNumber)) {
            return null;
        }

        String contactPersonId = Helper.generateId();

        return new ContactPerson.Builder()
                .setContactPersonId(contactPersonId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmailAddress(emailAddress)
                .setCellPhoneNumber(cellPhoneNumber)
                .build();
    }
}
