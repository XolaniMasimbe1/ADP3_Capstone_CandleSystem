package ac.za.cput.controller;
/*
 * ForgotPasswordControllerTest.java
 * Test for ForgotPasswordController
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.factory.AdminFactory;
import ac.za.cput.factory.RetailStoreFactory;
import ac.za.cput.util.ChangePassword;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ForgotPasswordControllerTest {

    @LocalServerPort
    private int port;

    private static RetailStore retailStore;
    private static Admin admin;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseURL() {
        return "http://localhost:" + port + "/CandleSystem/forgot-password";
    }

    @BeforeEach
    void setUp() {
        // Generate unique data for each test to avoid constraint violations
        String timestamp = String.valueOf(System.currentTimeMillis());
        retailStore = RetailStoreFactory.createRetailStore(
                "PicknPay_" + timestamp,
                "info" + timestamp + "@picknpay.com",
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
                "john" + timestamp + "@picknpay.com",
                "+27123456789",
                "Jane",
                "Smith",
                "jane" + timestamp + "@picknpay.com",
                "+27987654321"
        );

        admin = AdminFactory.createAdmin(
                "testadmin" + timestamp,
                "admin" + timestamp + "@test.com",
                "1234567890"
        );
    }

    @Test
    @Order(1)
    void a_VerifyMailForRetailStore() {
        assertNotNull(retailStore, "RetailStore should not be null before sending to API");
        System.out.println("RetailStore to verify mail: " + retailStore);

        String url = baseURL() + "/verifyMail/" + retailStore.getStoreEmail();
        System.out.println("Calling URL: " + url);
        
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            
            if (response.getStatusCode().value() == 404) {
                System.out.println("ERROR: 404 - Endpoint not found. Check if controller is properly registered.");
                System.out.println("Trying alternative URL without context path...");
                String altUrl = "http://localhost:" + port + "/forgot-password/verifyMail/" + retailStore.getStoreEmail();
                System.out.println("Alternative URL: " + altUrl);
                ResponseEntity<String> altResponse = restTemplate.postForEntity(altUrl, null, String.class);
                System.out.println("Alternative Response Status: " + altResponse.getStatusCode());
                System.out.println("Alternative Response Body: " + altResponse.getBody());
            }
            
            assertNotNull(response.getBody());
            assertEquals(200, response.getStatusCode().value());
            System.out.println("Verify Mail Response: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void b_VerifyMailForAdmin() {
        assertNotNull(admin, "Admin should not be null before sending to API");
        System.out.println("Admin to verify mail: " + admin);

        String url = baseURL() + "/admin/verifyMail/" + admin.getEmail();
        System.out.println("Calling URL: " + url);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody());
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        System.out.println("Verify Admin Mail Response: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_VerifyOtpForRetailStore() {
        // This test would need a valid OTP from the previous test
        // In a real scenario, you'd need to get the OTP from the database or email
        Integer testOtp = 123456; // This would be the actual OTP from the previous test
        
        String url = baseURL() + "/verifyOtp/" + testOtp + "/" + retailStore.getStoreEmail();
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        assertNotNull(response.getBody());
        System.out.println("Verify OTP Response: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_VerifyOtpForAdmin() {
        // This test would need a valid OTP from the previous test
        Integer testOtp = 654321; // This would be the actual OTP from the previous test
        
        String url = baseURL() + "/admin/verifyOtp/" + testOtp + "/" + admin.getEmail();
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        assertNotNull(response.getBody());
        System.out.println("Verify Admin OTP Response: " + response.getBody());
    }

    @Test
    @Order(5)
    void e_ChangePasswordForRetailStore() {
        ChangePassword changePassword = new ChangePassword("newPassword123", "newPassword123");
        
        String url = baseURL() + "/changePassword/" + retailStore.getStoreEmail();
        ResponseEntity<String> response = restTemplate.postForEntity(url, changePassword, String.class);
        assertNotNull(response.getBody());
        System.out.println("Change Password Response: " + response.getBody());
    }

    @Test
    @Order(6)
    void f_ChangePasswordForAdmin() {
        ChangePassword changePassword = new ChangePassword("newAdminPassword123", "newAdminPassword123");
        
        String url = baseURL() + "/admin/changePassword/" + admin.getEmail();
        ResponseEntity<String> response = restTemplate.postForEntity(url, changePassword, String.class);
        assertNotNull(response.getBody());
        System.out.println("Change Admin Password Response: " + response.getBody());
    }
}
