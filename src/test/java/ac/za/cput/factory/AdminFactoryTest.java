package ac.za.cput.factory;

import ac.za.cput.domain.Admin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {

    @Test
    void testCreateAdmin() {
        Admin admin = AdminFactory.createAdmin("admin123", "password123", "admin@example.com", "+1234567890");
        
        assertNotNull(admin);
        assertEquals("admin123", admin.getUsername());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("+1234567890", admin.getPhoneNumber());
        assertTrue(admin.getPasswordHash().startsWith("$2a$"));
        
        System.out.println("Created Admin: " + admin);
    }

    @Test
    void testCreateAdminWithNullValues() {
        Admin admin = AdminFactory.createAdmin(null, "password", "email@test.com", "+1234567890");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", null, "email@test.com", "+1234567890");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", null, "+1234567890");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", "email@test.com", null);
        assertNull(admin);
    }

    @Test
    void testCreateAdminWithEmptyValues() {
        Admin admin = AdminFactory.createAdmin("", "password", "email@test.com", "+1234567890");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "", "email@test.com", "+1234567890");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", "", "+1234567890");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", "email@test.com", "");
        assertNull(admin);
    }
}
