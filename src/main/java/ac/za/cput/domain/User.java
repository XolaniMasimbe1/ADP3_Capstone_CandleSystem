package ac.za.cput.domain;

import ac.za.cput.domain.Enum.UserRole;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id", referencedColumnName = "contactId")
    private ContactDetails contactDetails;

    // A public no-argument constructor is needed for Jackson deserialization
    public User() {}

    protected User(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.role = builder.role;
        this.contactDetails = builder.contactDetails;
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public ContactDetails getContactDetails() { return contactDetails; }

    // Public setters are required by Jackson for deserialization
    public void setUserId(String userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(UserRole role) { this.role = role; }
    public void setContactDetails(ContactDetails contactDetails) { this.contactDetails = contactDetails; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", contactDetails=" + contactDetails +
                '}';
    }

    public static class Builder {
        private String userId;
        private String username;
        private String passwordHash;
        private UserRole role;
        private ContactDetails contactDetails;

        public Builder setUserId(String userId) {
            this.userId = userId;
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

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setContactDetails(ContactDetails contactDetails) {
            this.contactDetails = contactDetails;
            return this;
        }

        public Builder copy(User user) {
            this.userId = user.userId;
            this.username = user.username;
            this.passwordHash = user.passwordHash;
            this.role = user.role;
            this.contactDetails = user.contactDetails;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
