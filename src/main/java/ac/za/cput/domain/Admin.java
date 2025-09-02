package ac.za.cput.domain;

import ac.za.cput.domain.Enum.UserRole;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    private String adminId;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    public Admin() {}

    protected Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.user = builder.user;
    }

    public String getAdminId() { return adminId; }
    public User getUser() { return user; }


    public void setAdminId(String adminId) { this.adminId = adminId; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", user=" + user +
                '}';
    }

    public static class Builder {
        private String adminId;
        private User user;

        public Builder setAdminId(String adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(Admin admin) {
            this.adminId = admin.adminId;
            this.user = admin.user;
            return this;
        }

        public Admin build() {
            return new Admin(this);
        }
    }
}
