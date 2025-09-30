package ac.za.cput.factory;

import ac.za.cput.domain.Admin;
import ac.za.cput.util.Helper;

public class AdminFactory {
    public static Admin createAdmin(String username, String passwordHash, String email, String phoneNumber) {
        if (Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(passwordHash) || 
            Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }

        String adminId = Helper.generateId();

        return new Admin.Builder()
                .setAdminId(adminId)
                .setUsername(username)
                .setPasswordHash(passwordHash)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }

    public static Admin createAdmin(String username, String email, String phoneNumber) {
        if (Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }

        String adminId = Helper.generateId();
        String defaultPassword = "admin123"; // Default password for testing

        return new Admin.Builder()
                .setAdminId(adminId)
                .setUsername(username)
                .setPasswordHash(defaultPassword)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .build();
    }
}
