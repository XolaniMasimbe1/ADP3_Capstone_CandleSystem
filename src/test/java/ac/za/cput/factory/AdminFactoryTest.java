package ac.za.cput.factory;

import ac.za.cput.domain.Admin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {

    @Test
    void testCreateAdmin() {
        Admin admin = AdminFactory.createAdmin("admin123", "password123", "admin@example.com", "+1234567890",
                "8000", "123 Admin Street", "Cape Town", "Western Cape", "South Africa");
        
        assertNotNull(admin);
        assertNotNull(admin.getAdminId());
        assertNotNull(admin.getUser());
        assertEquals("admin123", admin.getUser().getUsername());
        assertNotNull(admin.getUser().getContactDetails());
        assertEquals("admin@example.com", admin.getUser().getContactDetails().getEmail());
        assertEquals("+1234567890", admin.getUser().getContactDetails().getPhoneNumber());
        assertEquals("8000", admin.getUser().getContactDetails().getPostalCode());
        assertEquals("123 Admin Street", admin.getUser().getContactDetails().getStreet());
        assertEquals("Cape Town", admin.getUser().getContactDetails().getCity());
        assertEquals("Western Cape", admin.getUser().getContactDetails().getProvince());
        assertEquals("South Africa", admin.getUser().getContactDetails().getCountry());
        assertTrue(admin.getUser().getPasswordHash().startsWith("$2a$"));
        
        System.out.println("Created Admin: " + admin);
    }

    @Test
    void testCreateAdminWithNullValues() {
        Admin admin = AdminFactory.createAdmin(null, "password", "email@test.com", "+1234567890",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", null, "email@test.com", "+1234567890",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", null, "+1234567890",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", "email@test.com", null,
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        System.out.println("Null validation tests passed");
    }

    @Test
    void testCreateAdminWithEmptyValues() {
        Admin admin = AdminFactory.createAdmin("", "password", "email@test.com", "+1234567890",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "", "email@test.com", "+1234567890",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", "", "+1234567890",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        admin = AdminFactory.createAdmin("username", "password", "email@test.com", "",
                "8000", "123 Street", "City", "Province", "Country");
        assertNull(admin);
        
        System.out.println("Empty validation tests passed");
    }
}
