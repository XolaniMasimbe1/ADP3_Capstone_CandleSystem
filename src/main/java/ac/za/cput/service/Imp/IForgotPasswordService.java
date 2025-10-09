package ac.za.cput.service.Imp;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IForgotPasswordService extends IService<ForgotPassword, Integer> {
    List<ForgotPassword> getAll();
    
    ForgotPassword createForgotPasswordForRetailStore(RetailStore retailStore);
    
    ForgotPassword createForgotPasswordForAdmin(Admin admin);
    
    Optional<ForgotPassword> findByOtpAndRetailStore(Integer otp, RetailStore retailStore);
    
    Optional<ForgotPassword> findByOtpAndAdmin(Integer otp, Admin admin);
    
    boolean verifyOtpForRetailStore(Integer otp, String email);
    
    boolean verifyOtpForAdmin(Integer otp, String email);
    
    boolean isOtpExpired(ForgotPassword forgotPassword);
    
    void deleteExpiredOtp(Integer id);
    
    Optional<ForgotPassword> findByRetailStore(RetailStore retailStore);
    
    Optional<ForgotPassword> findByAdmin(Admin admin);
}
