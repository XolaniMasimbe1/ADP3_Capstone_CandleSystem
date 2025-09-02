package ac.za.cput.domain;

import ac.za.cput.domain.Enum.UserRole;
import jakarta.persistence.*;


@Entity
@Table(name = "retail_store")
public class RetailStore {
    @Id
    private String storeId;
    
    @Column(name = "store_number", unique = true)
    private String storeNumber;
    
    @Column(name = "store_name")
    private String storeName;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // A public no-argument constructor is needed for Jackson deserialization
    public RetailStore() {}

    protected RetailStore(Builder builder) {
        this.storeId = builder.storeId;
        this.storeNumber = builder.storeNumber;
        this.storeName = builder.storeName;
        this.user = builder.user;
    }

    // Getters
    public String getStoreId() { return storeId; }
    public String getStoreNumber() { return storeNumber; }
    public String getStoreName() { return storeName; }
    public User getUser() { return user; }

    // Public setters are required by Jackson
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RetailStore{" +
                "storeId='" + storeId + '\'' +
                ", storeNumber='" + storeNumber + '\'' +
                ", storeName='" + storeName + '\'' +
                ", user=" + user +
                '}';
    }

    public static class Builder {
        private String storeId;
        private String storeNumber;
        private String storeName;
        private User user;

        public Builder setStoreId(String storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder setStoreNumber(String storeNumber) {
            this.storeNumber = storeNumber;
            return this;
        }

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(RetailStore retailStore) {
            this.storeId = retailStore.storeId;
            this.storeNumber = retailStore.storeNumber;
            this.storeName = retailStore.storeName;
            this.user = retailStore.user;
            return this;
        }

        public RetailStore build() {
            return new RetailStore(this);
        }
    }
}
