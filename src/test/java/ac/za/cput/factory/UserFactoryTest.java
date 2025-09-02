package ac.za.cput.factory;

import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void createUser() {
        User user = UserFactory.createUser("test_user", "password123", UserRole.STORE, "user@example.com", "+1234567890",
                "8000", "456 User Avenue", "Cape Town", "Western Cape", "South Africa");
        
        assertNotNull(user);
        assertNotNull(user.getUserId());
        assertEquals("test_user", user.getUsername());
        assertEquals(UserRole.STORE, user.getRole());
        assertNotNull(user.getContactDetails());
        assertEquals("user@example.com", user.getContactDetails().getEmail());
        assertEquals("+1234567890", user.getContactDetails().getPhoneNumber());
        assertEquals("8000", user.getContactDetails().getPostalCode());
        assertEquals("456 User Avenue", user.getContactDetails().getStreet());
        assertEquals("Cape Town", user.getContactDetails().getCity());
        assertEquals("Western Cape", user.getContactDetails().getProvince());
        assertEquals("South Africa", user.getContactDetails().getCountry());
        
        System.out.println("Created User: " + user);
    }

    @Test
    void createUserWithNullValues() {
        User user = UserFactory.createUser(null, "password123", UserRole.STORE, "user@example.com", "+1234567890",
                "8000", "456 User Avenue", "Cape Town", "Western Cape", "South Africa");
        assertNull(user);
        
        user = UserFactory.createUser("test_user", null, UserRole.STORE, "user@example.com", "+1234567890",
                "8000", "456 User Avenue", "Cape Town", "Western Cape", "South Africa");
        assertNull(user);
        
        user = UserFactory.createUser("test_user", "password123", null, "user@example.com", "+1234567890",
                "8000", "456 User Avenue", "Cape Town", "Western Cape", "South Africa");
        assertNull(user);
        
        System.out.println("Null validation tests passed");
    }
}