package ac.za.cput.service;
/*
 * ForgotPasswordServiceTest.java
 * Test for ForgotPasswordService
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.factory.AdminFactory;
import ac.za.cput.factory.ForgotPasswordFactory;
import ac.za.cput.factory.RetailStoreFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ForgotPasswordServiceTest {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    private static RetailStore retailStore = RetailStoreFactory.createRetailStore(
            "PicknPay",
            "info@picknpay.com",
            "password123",
            "123",
            "Main Street",
            "CBD",
            "Cape Town",
            Province.WESTERN_CAPE,
            "8001",
            "South Africa",
            "John",
            "Doe",
            "john@picknpay.com",
            "+27123456789",
            "Jane",
            "Smith",
            "jane@picknpay.com",
            "+27987654321"
    );

    private static Admin admin = AdminFactory.createAdmin(
            "testadmin",
            "admin@test.com",
            "1234567890"
    );

    private static ForgotPassword forgotPassword;

    @Test
    void create() {
        forgotPassword = forgotPasswordService.createForgotPasswordForRetailStore(retailStore);
        assertNotNull(forgotPassword);
        System.out.println("Created: " + forgotPassword);
    }

    @Test
    void read() {
        ForgotPassword readForgotPassword = forgotPasswordService.read(forgotPassword.getId());
        assertNotNull(readForgotPassword);
        System.out.println("Read: " + readForgotPassword);
    }

    @Test
    void update() {
        ForgotPassword updatedForgotPassword = forgotPasswordService.update(forgotPassword);
        assertNotNull(updatedForgotPassword);
        System.out.println("Updated: " + updatedForgotPassword);
    }

    @Test
    void getAll() {
        var forgotPasswords = forgotPasswordService.getAll();
        assertFalse(forgotPasswords.isEmpty());
        System.out.println("All ForgotPasswords: " + forgotPasswords);
    }

    @Test
    void createForgotPasswordForAdmin() {
        ForgotPassword adminForgotPassword = forgotPasswordService.createForgotPasswordForAdmin(admin);
        assertNotNull(adminForgotPassword);
        System.out.println("Created ForgotPassword for Admin: " + adminForgotPassword);
    }

    @Test
    void verifyOtpForRetailStore() {
        boolean isValid = forgotPasswordService.verifyOtpForRetailStore(forgotPassword.getOtp(), "info@picknpay.com");
        assertTrue(isValid);
        System.out.println("OTP verification for retail store: " + isValid);
    }

    @Test
    void verifyOtpForAdmin() {
        ForgotPassword adminForgotPassword = forgotPasswordService.createForgotPasswordForAdmin(admin);
        boolean isValid = forgotPasswordService.verifyOtpForAdmin(adminForgotPassword.getOtp(), "admin@test.com");
        assertTrue(isValid);
        System.out.println("OTP verification for admin: " + isValid);
    }
}
