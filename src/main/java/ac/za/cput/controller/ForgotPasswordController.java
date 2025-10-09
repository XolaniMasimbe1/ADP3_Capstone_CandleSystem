package ac.za.cput.controller;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.AdminRepository;
import ac.za.cput.repository.RetailStoreRepository;
import ac.za.cput.service.EmailService;
import ac.za.cput.service.ForgotPasswordService;
import ac.za.cput.util.ChangePassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/forgot-password")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    private final RetailStoreRepository storeRepository;
    
    private final AdminRepository adminRepository;

    private final ForgotPasswordService forgotPasswordService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(RetailStoreRepository storeRepository, 
                                   AdminRepository adminRepository,
                                   ForgotPasswordService forgotPasswordService, 
                                   EmailService emailService, 
                                   PasswordEncoder passwordEncoder) {
        this.storeRepository = storeRepository;
        this.adminRepository = adminRepository;
        this.forgotPasswordService = forgotPasswordService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    //Send email for email verification (works for both admin and retail store)
    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyMail(@PathVariable String email) {
        System.out.println("ForgotPasswordController.verifyMail called with email: " + email);
        // Try to find as retail store first
        Optional<RetailStore> retailStoreOpt = storeRepository.findByStoreEmail(email);
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        
        if (retailStoreOpt.isEmpty() && adminOpt.isEmpty()) {
            throw new IllegalArgumentException("Please provide valid email: " + email);
        }
        
        ForgotPassword forgotPassword;
        if (retailStoreOpt.isPresent()) {
            forgotPassword = forgotPasswordService.createForgotPasswordForRetailStore(retailStoreOpt.get());
        } else {
            forgotPassword = forgotPasswordService.createForgotPasswordForAdmin(adminOpt.get());
        }
        
        if (forgotPassword == null) {
            return ResponseEntity.badRequest().body("Failed to create forgot password request");
        }
        
        // Send email without DTO
        String subject = "OTP for Forgot Password request";
        String text = "This is your OTP for Forgot Password request: " + forgotPassword.getOtp();
        emailService.sendSimpleMessage(email, subject, text);
        
        return ResponseEntity.ok("Email sent successfully to " + email);
    }

   @PostMapping("/verifyOtp/{otp}/{email}")
   public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email){
        // Try to find as retail store first
        Optional<RetailStore> retailStoreOpt = storeRepository.findByStoreEmail(email);
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        
        if (retailStoreOpt.isEmpty() && adminOpt.isEmpty()) {
            throw new IllegalArgumentException("Please provide valid email: " + email);
        }
        
        boolean isValidOtp;
        if (retailStoreOpt.isPresent()) {
            isValidOtp = forgotPasswordService.verifyOtpForRetailStore(otp, email);
        } else {
            isValidOtp = forgotPasswordService.verifyOtpForAdmin(otp, email);
        }
        
        if (!isValidOtp) {
            return new ResponseEntity<>("Invalid OTP or OTP has expired. Please request a new one.", HttpStatus.EXPECTATION_FAILED);
        }
        
        return ResponseEntity.ok("OTP verified successfully. You can now reset your password.");
   }

   @PostMapping("/changePassword/{email}")
   public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                       @PathVariable String email){
        if(!Objects.equals(changePassword.password(), changePassword.confirmPassword())){
            return new ResponseEntity<>("Password and Confirm Password do not match", HttpStatus.BAD_REQUEST);
        }
        
        // Try to find as retail store first
        Optional<RetailStore> retailStoreOpt = storeRepository.findByStoreEmail(email);
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        
        if (retailStoreOpt.isEmpty() && adminOpt.isEmpty()) {
            throw new IllegalArgumentException("Please provide valid email: " + email);
        }
        
        String hashedPassword = passwordEncoder.encode(changePassword.password());
        
        if (retailStoreOpt.isPresent()) {
            RetailStore retailStore = retailStoreOpt.get();
            retailStore.setPasswordHash(hashedPassword);
            storeRepository.save(retailStore);
        } else {
            Admin admin = adminOpt.get();
            admin.setPasswordHash(hashedPassword);
            adminRepository.save(admin);
        }
        
        return ResponseEntity.ok("Password changed successfully");
   }


    // Admin-specific forgot password endpoints
    @PostMapping("/admin/verifyMail/{email}")
    public ResponseEntity<String> verifyAdminMail(@PathVariable String email) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Please provide valid admin email: " + email));
        
        ForgotPassword forgotPassword = forgotPasswordService.createForgotPasswordForAdmin(admin);
        
        if (forgotPassword == null) {
            return ResponseEntity.badRequest().body("Failed to create forgot password request");
        }
        
        // Send email without DTO
        String subject = "OTP for Admin Forgot Password request";
        String text = "This is your OTP for Admin Forgot Password request: " + forgotPassword.getOtp();
        emailService.sendSimpleMessage(email, subject, text);
        
        return ResponseEntity.ok("Email sent successfully to admin " + email);
    }

    @PostMapping("/admin/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyAdminOtp(@PathVariable Integer otp, @PathVariable String email){
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Please provide valid admin email: " + email));
        
        boolean isValidOtp = forgotPasswordService.verifyOtpForAdmin(otp, email);
        
        if (!isValidOtp) {
            return new ResponseEntity<>("Invalid OTP or OTP has expired. Please request a new one.", HttpStatus.EXPECTATION_FAILED);
        }
        
        return ResponseEntity.ok("OTP verified successfully. You can now reset your admin password.");
    }

    @PostMapping("/admin/changePassword/{email}")
    public ResponseEntity<String> changeAdminPasswordHandler(@RequestBody ChangePassword changePassword,
                                                           @PathVariable String email){
        if(!Objects.equals(changePassword.password(), changePassword.confirmPassword())){
            return new ResponseEntity<>("Password and Confirm Password do not match", HttpStatus.BAD_REQUEST);
        }
        
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Please provide valid admin email: " + email));
        
        String hashedPassword = passwordEncoder.encode(changePassword.password());
        admin.setPasswordHash(hashedPassword);
        adminRepository.save(admin);
        
        return ResponseEntity.ok("Admin password changed successfully");
    }

}
