package ac.za.cput.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer otp;

    private Date expirationTime;

    @OneToOne
    private RetailStore retailStore;
    
    @OneToOne
    private Admin admin;

    // Getters
    public Integer getId() {
        return id;
    }

    public Integer getOtp() {
        return otp;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public RetailStore getRetailStore() {
        return retailStore;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setRetailStore(RetailStore retailStore) {
        this.retailStore = retailStore;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
