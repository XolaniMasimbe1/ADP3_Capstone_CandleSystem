package ac.za.cput.factory;
/*
 * ForgotPasswordFactoryTest.java
 * Test for ForgotPasswordFactory
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Enum.Province;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForgotPasswordFactoryTest {

    private static RetailStore retailStore = new RetailStore.Builder()
            .setStoreId("store123")
            .setStoreName("Test Store")
            .setStoreEmail("test@store.com")
            .setPasswordHash("hashedPassword")
            .build();

    private static Admin admin = new Admin.Builder()
            .setAdminId("admin123")
            .setUsername("testadmin")
            .setPasswordHash("hashedPassword")
            .setEmail("admin@test.com")
            .setPhoneNumber("1234567890")
            .build();

    @Test
    void createForgotPasswordForRetailStore() {
        ForgotPassword forgotPassword = ForgotPasswordFactory.createForgotPasswordForRetailStore(retailStore);
        assertNotNull(forgotPassword);
        System.out.println("Created ForgotPassword for RetailStore: " + forgotPassword);
    }

    @Test
    void createForgotPasswordForAdmin() {
        ForgotPassword forgotPassword = ForgotPasswordFactory.createForgotPasswordForAdmin(admin);
        assertNotNull(forgotPassword);
        System.out.println("Created ForgotPassword for Admin: " + forgotPassword);
    }

    @Test
    void createForgotPasswordWithNullRetailStore() {
        ForgotPassword forgotPassword = ForgotPasswordFactory.createForgotPasswordForRetailStore(null);
        assertNull(forgotPassword);
        System.out.println("Null RetailStore test passed");
    }

    @Test
    void createForgotPasswordWithNullAdmin() {
        ForgotPassword forgotPassword = ForgotPasswordFactory.createForgotPasswordForAdmin(null);
        assertNull(forgotPassword);
        System.out.println("Null Admin test passed");
    }
}
