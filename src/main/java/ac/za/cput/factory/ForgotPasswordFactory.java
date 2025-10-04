package ac.za.cput.factory;
/*
 * ForgotPasswordFactory.java
 * Factory for ForgotPassword
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.util.Helper;

import java.util.Date;

public class ForgotPasswordFactory {
    
    public static ForgotPassword createForgotPasswordForRetailStore(RetailStore retailStore) {
        if (retailStore == null) {
            return null;
        }
        
        int otp = generateOTP();
        Date expirationTime = new Date(System.currentTimeMillis() + 70 * 1000); // 70 seconds from now
        
        return ForgotPassword.builder()
                .otp(otp)
                .expirationTime(expirationTime)
                .retailStore(retailStore)
                .build();
    }
    
    public static ForgotPassword createForgotPasswordForAdmin(Admin admin) {
        if (admin == null) {
            return null;
        }
        
        int otp = generateOTP();
        Date expirationTime = new Date(System.currentTimeMillis() + 70 * 1000); // 70 seconds from now
        
        return ForgotPassword.builder()
                .otp(otp)
                .expirationTime(expirationTime)
                .admin(admin)
                .build();
    }
    
    public static ForgotPassword createForgotPassword(Integer otp, Date expirationTime, RetailStore retailStore, Admin admin) {
        if (otp == null || expirationTime == null) {
            return null;
        }
        
        return ForgotPassword.builder()
                .otp(otp)
                .expirationTime(expirationTime)
                .retailStore(retailStore)
                .admin(admin)
                .build();
    }
    
    private static Integer generateOTP() {
        return (int) (Math.random() * 900000) + 100000; // Generate 6-digit OTP
    }
}
