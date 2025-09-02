package ac.za.cput.service;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;
import ac.za.cput.factory.UserFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    private static User user = UserFactory.createUser("er10", "rd8", UserRole.STORE, "test@example.com", "+1234567890",
            "8000", "456 User Street", "Cape Town", "Western Cape", "South Africa");

    @Test
    @Order(1)
    void create() {
        User createdUser = userService.create(user);
        assertNotNull(createdUser);
        System.out.println("Created: " + createdUser);
    }

    @Test
    @Order(1)
    void read() {
        User readUser = userService.read(user.getUserId());
        assertNotNull(readUser);
        System.out.println("Read: " + readUser);
    }

    @Test
    @Order(2)
    void update() {
        // Use factory to create updated user with proper validation and password hashing
        User updatedUser = UserFactory.createUser("userUpdated", "newPassword123", UserRole.ADMIN, "updated@example.com", "+0987654321",
                "8001", "789 Updated Avenue", "Cape Town", "Western Cape", "South Africa");
        updatedUser.setUserId(user.getUserId()); // Keep the same ID for update
        User result = userService.update(updatedUser);
        assertNotNull(result);
        System.out.println("Updated: " + result);
    }

    @Test
    @Order(3)
    void getAll() {
        var users = userService.getAll();
        assertFalse(users.isEmpty());
        System.out.println("All Users: " + users);
    }


}