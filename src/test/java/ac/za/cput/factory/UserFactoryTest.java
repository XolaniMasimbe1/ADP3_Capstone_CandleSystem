package ac.za.cput.factory;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    private static User user = UserFactory.createUser(
            "er10",
            "rd8",
            UserRole.STORE
    );

    @Test
    void createUser() {
        assertNotNull(user);
        System.out.println("Created: " + user);
    }
}