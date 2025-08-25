package ac.za.cput.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class RetailStore {
    @Id
    private String storeNumber;
    private String storeName;

    @Embedded
    private ContactDetails contactDetails;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // A public no-argument constructor is needed for Jackson deserialization
    public RetailStore() {}

    private RetailStore(Builder builder) {
        this.storeNumber = builder.storeNumber;
        this.storeName = builder.storeName;
        this.contactDetails = builder.contactDetails;
        this.user = builder.user;
    }

    // Getters
    public String getStoreNumber() { return storeNumber; }
    public String getStoreName() { return storeName; }
    public ContactDetails getContactDetails() { return contactDetails; }
    public User getUser() { return user; }

    // Public setters are required by Jackson
    public void setStoreNumber(String storeNumber) {
        this.storeNumber = storeNumber;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetailStore that = (RetailStore) o;
        return Objects.equals(storeNumber, that.storeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeNumber);
    }

    @Override
    public String toString() {
        return "RetailStore{" +
                "storeNumber='" + storeNumber + '\'' +
                ", storeName='" + storeName + '\'' +
                ", contactDetails=" + contactDetails +
                '}';
    }

    public static class Builder {
        private String storeNumber;
        private String storeName;
        private ContactDetails contactDetails;
        private User user;

        public Builder setStoreNumber(String storeNumber) {
            this.storeNumber = storeNumber;
            return this;
        }

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public Builder setContactDetails(ContactDetails contactDetails) {
            this.contactDetails = contactDetails;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(RetailStore retailStore) {
            this.storeNumber = retailStore.storeNumber;
            this.storeName = retailStore.storeName;
            this.contactDetails = retailStore.contactDetails;
            this.user = retailStore.user;
            return this;
        }

        public RetailStore build() {
            return new RetailStore(this);
        }
    }
}
