package ac.za.cput.factory;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.User;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.util.Helper;

public class AdminFactory {

    public static Admin createAdmin(String username, String password, String email, String phoneNumber, 
                                   String postalCode, String street, String city, String province, String country) {
        if (Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(password) || 
            Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }
        
        String adminId = Helper.generateId();
        
        // Create User first with all contact details
        User user = UserFactory.createUser(username, password, UserRole.ADMIN, email, phoneNumber, 
                                          postalCode, street, city, province, country);
        
        // Create Admin with reference to User
        Admin.Builder builder = new Admin.Builder();
        return builder
                .setAdminId(adminId)
                .setUser(user)
                .build();
    }
}
