package ac.za.cput.factory;

import ac.za.cput.domain.Admin;
import ac.za.cput.util.Helper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminFactory {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static Admin createAdmin(String username, String password, String email, String phoneNumber) {
        if (Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(password) || 
            Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }
        
        String adminId = Helper.generateId();
        String passwordHash = passwordEncoder.encode(password);
        
        return new Admin.Builder()
                .setAdminId(adminId)
                .setUsername(username)
                .setPasswordHash(passwordHash)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }
}
