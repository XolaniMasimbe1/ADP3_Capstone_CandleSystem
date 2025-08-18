package ac.za.cput.factory;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;

import ac.za.cput.util.Helper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserFactory {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User createUser(String username, String password, UserRole role) {
        if (Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(password) || role == null) {
            return null;
        }
        String userId = Helper.generateId();
        String passwordHash = passwordEncoder.encode(password);
        return new User.Builder()
                .setUserId(userId)
                .setUsername(username)
                .setPasswordHash(passwordHash)
                .setRole(role)
                .build();
    }
}