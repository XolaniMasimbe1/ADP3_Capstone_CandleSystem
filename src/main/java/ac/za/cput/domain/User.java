package ac.za.cput.domain;

import ac.za.cput.domain.Enum.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class User {
    @Id
    private String userId;
    @Column(unique = true)
    private String username;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private RetailStore retailStore;

    public User() {}

    private User(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.role = builder.role;
        if (builder.retailStore != null) {
            this.retailStore = builder.retailStore;
            this.retailStore.setUser(this);
        }
    }

    // Getters
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public UserRole getRole() { return role; }
    public RetailStore getRetailStore() { return retailStore; }

    // Setters
    public void setUserId(String userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setRole(UserRole role) { this.role = role; }

    public void setRetailStore(RetailStore retailStore) {
        if (this.retailStore != null && !this.retailStore.equals(retailStore)) {
            this.retailStore.setUser(null);
        }
        this.retailStore = retailStore;
        if (retailStore != null && retailStore.getUser() != this) {
            retailStore.setUser(this);
        }
    }

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
                '}'; // Excluded retailStore to avoid circular reference
    }

    public static class Builder {
        private String userId;
        private String username;
        private String passwordHash;
        private UserRole role;
        private RetailStore retailStore;

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

        public Builder setRetailStore(RetailStore retailStore) {
            this.retailStore = retailStore;
            return this;
        }

        public Builder copy(User user) {
            this.userId = user.userId;
            this.username = user.username;
            this.passwordHash = user.passwordHash;
            this.role = user.role;
            this.retailStore = user.retailStore;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}