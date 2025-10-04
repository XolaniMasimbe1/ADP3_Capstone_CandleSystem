package ac.za.cput.service;
/*
 * ForgotPasswordService.java
 * Service for ForgotPassword
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.AdminRepository;
import ac.za.cput.repository.ForgotPasswordRepository;
import ac.za.cput.repository.RetailStoreRepository;
import ac.za.cput.service.Imp.IForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ForgotPasswordService implements IForgotPasswordService {
    
    private final ForgotPasswordRepository repository;
    private final RetailStoreRepository retailStoreRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public ForgotPasswordService(ForgotPasswordRepository repository, 
                                RetailStoreRepository retailStoreRepository,
                                AdminRepository adminRepository) {
        this.repository = repository;
        this.retailStoreRepository = retailStoreRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public ForgotPassword create(ForgotPassword forgotPassword) {
        return this.repository.save(forgotPassword);
    }

    @Override
    public ForgotPassword read(Integer id) {
        return this.repository.findById(id).orElse(null);
    }

    @Override
    public ForgotPassword update(ForgotPassword forgotPassword) {
        return this.repository.save(forgotPassword);
    }

    @Override
    public List<ForgotPassword> getAll() {
        return this.repository.findAll();
    }

    @Override
    public ForgotPassword createForgotPasswordForRetailStore(RetailStore retailStore) {
        if (retailStore == null) {
            return null;
        }
        
        ForgotPassword forgotPassword = ac.za.cput.factory.ForgotPasswordFactory.createForgotPasswordForRetailStore(retailStore);
        return this.repository.save(forgotPassword);
    }

    @Override
    public ForgotPassword createForgotPasswordForAdmin(Admin admin) {
        if (admin == null) {
            return null;
        }
        
        ForgotPassword forgotPassword = ac.za.cput.factory.ForgotPasswordFactory.createForgotPasswordForAdmin(admin);
        return this.repository.save(forgotPassword);
    }

    @Override
    public Optional<ForgotPassword> findByOtpAndRetailStore(Integer otp, RetailStore retailStore) {
        return this.repository.findByOtpAndRetailStore(otp, retailStore);
    }

    @Override
    public Optional<ForgotPassword> findByOtpAndAdmin(Integer otp, Admin admin) {
        return this.repository.findByOtpAndAdmin(otp, admin);
    }

    @Override
    public boolean verifyOtpForRetailStore(Integer otp, String email) {
        Optional<RetailStore> retailStoreOpt = retailStoreRepository.findByStoreEmail(email);
        if (retailStoreOpt.isEmpty()) {
            return false;
        }
        
        Optional<ForgotPassword> forgotPasswordOpt = findByOtpAndRetailStore(otp, retailStoreOpt.get());
        if (forgotPasswordOpt.isEmpty()) {
            return false;
        }
        
        ForgotPassword forgotPassword = forgotPasswordOpt.get();
        if (isOtpExpired(forgotPassword)) {
            deleteExpiredOtp(forgotPassword.getId());
            return false;
        }
        
        return true;
    }

    @Override
    public boolean verifyOtpForAdmin(Integer otp, String email) {
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isEmpty()) {
            return false;
        }
        
        Optional<ForgotPassword> forgotPasswordOpt = findByOtpAndAdmin(otp, adminOpt.get());
        if (forgotPasswordOpt.isEmpty()) {
            return false;
        }
        
        ForgotPassword forgotPassword = forgotPasswordOpt.get();
        if (isOtpExpired(forgotPassword)) {
            deleteExpiredOtp(forgotPassword.getId());
            return false;
        }
        
        return true;
    }

    @Override
    public boolean isOtpExpired(ForgotPassword forgotPassword) {
        return forgotPassword.getExpirationTime().before(Date.from(Instant.now()));
    }

    @Override
    public void deleteExpiredOtp(Integer id) {
        this.repository.deleteById(id);
    }
}
