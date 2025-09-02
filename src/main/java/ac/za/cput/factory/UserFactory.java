package ac.za.cput.factory;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;
import ac.za.cput.domain.ContactDetails;
import ac.za.cput.util.Helper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFactory {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User createUser(String username, String password, UserRole role, String email, String phoneNumber, String postalCode, String street, String city, String province, String country) {
        if (Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(password) || role == null) {
            return null;
        }
        
        String userId = Helper.generateId();
        String passwordHash = passwordEncoder.encode(password);
        ContactDetails contactDetails = ContactDetailsFactory.createContactDetails(email, phoneNumber, postalCode, street, city, province, country);
        
        User.Builder builder = new User.Builder();
        return builder
                .setUserId(userId)
                .setUsername(username)
                .setPasswordHash(passwordHash)
                .setRole(role)
                .setContactDetails(contactDetails)
                .build();
    }
}