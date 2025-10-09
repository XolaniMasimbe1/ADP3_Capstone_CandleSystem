package ac.za.cput.repository;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.ForgotPassword;
import ac.za.cput.domain.RetailStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("SELECT f FROM ForgotPassword f WHERE f.otp = ?1 AND f.retailStore = ?2")
    Optional<ForgotPassword> findByOtpAndRetailStore(Integer otp, RetailStore retailStore);
    
    @Query("SELECT f FROM ForgotPassword f WHERE f.otp = ?1 AND f.admin = ?2")
    Optional<ForgotPassword> findByOtpAndAdmin(Integer otp, Admin admin);
    
    @Query("SELECT f FROM ForgotPassword f WHERE f.retailStore = ?1")
    Optional<ForgotPassword> findByRetailStore(RetailStore retailStore);
    
    @Query("SELECT f FROM ForgotPassword f WHERE f.admin = ?1")
    Optional<ForgotPassword> findByAdmin(Admin admin);
}
