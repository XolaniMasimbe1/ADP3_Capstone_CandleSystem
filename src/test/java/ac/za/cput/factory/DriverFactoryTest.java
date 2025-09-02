package ac.za.cput.factory;

import ac.za.cput.domain.Driver;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DriverFactoryTest {

    @Test
    void testCreateDriver() {
        Driver driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        
        assertNotNull(driver);
        assertNotNull(driver.getDriverId());
        assertEquals("DL123456789", driver.getLicenseNumber());
        assertEquals("Van", driver.getVehicleType());
        assertNotNull(driver.getUser());
        assertEquals("driver123", driver.getUser().getUsername());
        assertNotNull(driver.getUser().getContactDetails());
        assertEquals("driver@example.com", driver.getUser().getContactDetails().getEmail());
        assertEquals("+1234567890", driver.getUser().getContactDetails().getPhoneNumber());
        assertEquals("7500", driver.getUser().getContactDetails().getPostalCode());
        assertEquals("789 Driver Drive", driver.getUser().getContactDetails().getStreet());
        assertEquals("Cape Town", driver.getUser().getContactDetails().getCity());
        assertEquals("Western Cape", driver.getUser().getContactDetails().getProvince());
        assertEquals("South Africa", driver.getUser().getContactDetails().getCountry());
        assertTrue(driver.getUser().getPasswordHash().startsWith("$2a$"));
        
        System.out.println("Created Driver: " + driver);
    }

    @Test
    void testCreateDriverWithNullValues() {
        Driver driver = DriverFactory.createDriver(null, "Van", "driver123", "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", null, "driver123", "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", null, "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", null, 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", "password123", 
                null, "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", "password123", 
                "driver@example.com", null, "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        System.out.println("Null validation tests passed");
    }

    @Test
    void testCreateDriverWithEmptyValues() {
        Driver driver = DriverFactory.createDriver("", "Van", "driver123", "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "", "driver123", "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "", "password123", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", "", 
                "driver@example.com", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", "password123", 
                "", "+1234567890", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        driver = DriverFactory.createDriver("DL123456789", "Van", "driver123", "password123", 
                "driver@example.com", "", "7500", "789 Driver Drive", "Cape Town", "Western Cape", "South Africa");
        assertNull(driver);
        
        System.out.println("Empty validation tests passed");
    }
}
