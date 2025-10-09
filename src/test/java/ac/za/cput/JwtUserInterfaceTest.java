package ac.za.cput;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test to verify that JWT implementation works correctly with User interface
 * This follows DDD principles by testing the User interface contract
 */
public class JwtUserInterfaceTest {

    @Test
    public void testAdminImplementsUserInterface() {
        // Create Admin instance
        Admin admin = new Admin();
        admin.setAdminId("admin123");
        admin.setUsername("testadmin");
        admin.setEmail("admin@test.com");
        admin.setPasswordHash("hashedpassword");

        // Test User interface methods
        assertEquals("admin123", admin.getId());
        assertEquals("admin@test.com", admin.getEmail());
        assertEquals("hashedpassword", admin.getPasswordHash());
        assertEquals("ADMIN", admin.getRole());
        assertEquals("testadmin", admin.getName());

        // Verify it implements User interface
        assertTrue(admin instanceof User);
    }

    @Test
    public void testRetailStoreImplementsUserInterface() {
        // Create RetailStore instance
        RetailStore store = new RetailStore();
        store.setStoreId("store123");
        store.setStoreName("Test Store");
        store.setStoreEmail("store@test.com");
        store.setPasswordHash("hashedpassword");

        // Test User interface methods
        assertEquals("store123", store.getId());
        assertEquals("store@test.com", store.getEmail());
        assertEquals("hashedpassword", store.getPasswordHash());
        assertEquals("RETAIL_STORE", store.getRole());
        assertEquals("Test Store", store.getName());

        // Verify it implements User interface
        assertTrue(store instanceof User);
    }

    @Test
    public void testUserInterfaceContract() {
        // Test that both entities follow the same User interface contract
        Admin admin = new Admin();
        admin.setAdminId("admin123");
        admin.setUsername("admin");
        admin.setEmail("admin@test.com");
        admin.setPasswordHash("password");

        RetailStore store = new RetailStore();
        store.setStoreId("store123");
        store.setStoreName("store");
        store.setStoreEmail("store@test.com");
        store.setPasswordHash("password");

        // Both should implement User interface
        assertTrue(admin instanceof User);
        assertTrue(store instanceof User);

        // Both should have the same interface methods
        assertNotNull(admin.getId());
        assertNotNull(admin.getEmail());
        assertNotNull(admin.getPasswordHash());
        assertNotNull(admin.getRole());
        assertNotNull(admin.getName());

        assertNotNull(store.getId());
        assertNotNull(store.getEmail());
        assertNotNull(store.getPasswordHash());
        assertNotNull(store.getRole());
        assertNotNull(store.getName());
    }
}
