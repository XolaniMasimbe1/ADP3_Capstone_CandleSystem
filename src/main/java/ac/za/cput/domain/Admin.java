package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String adminId;
    
    @Column(unique = true)
    private String username;
    
    private String passwordHash;
    private String email;
    private String phoneNumber;
    

    // A public no-argument constructor is needed for Jackson deserialization
    public Admin() {}

    protected Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
    }

    // Getters
    public String getAdminId() { return adminId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    // Public setters are required by Jackson for deserialization
    public void setAdminId(String adminId) { this.adminId = adminId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public static class Builder {
        private String adminId;
        private String username;
        private String passwordHash;
        private String email;
        private String phoneNumber;

        public Builder setAdminId(String adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder copy(Admin admin) {
            this.adminId = admin.adminId;
            this.username = admin.username;
            this.passwordHash = admin.passwordHash;
            this.email = admin.email;
            this.phoneNumber = admin.phoneNumber;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
