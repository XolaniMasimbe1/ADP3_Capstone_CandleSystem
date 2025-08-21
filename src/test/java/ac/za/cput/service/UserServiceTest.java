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

    private static User user = UserFactory.createUser("er10", "rd8", UserRole.STORE);

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
        User updatedUser = new User.Builder().
                 setPasswordHash("newPasswordHash")
                .setUsername("userUpdated")
                .setRole(UserRole.ADMIN)
                .setUserId(user.getUserId())
                .build();
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