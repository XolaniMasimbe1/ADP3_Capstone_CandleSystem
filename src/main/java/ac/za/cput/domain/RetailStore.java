package ac.za.cput.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "retail_store")
public class RetailStore {
    @Id
    private String storeId;
    
    @Column(name = "store_name")
    private String storeName;
    
    @Column(name = "store_email", unique = true)
    private String storeEmail;
    
    @Column(name = "password_hash")
    private String passwordHash;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private Address address;
    
    // Contact persons relationship
    @OneToMany(mappedBy = "retailStore", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference("retailStore")
    private List<ContactPerson> contactPersons = new ArrayList<>();
    
     @OneToOne(mappedBy = "retailStore")
    private ForgotPassword forgotPassword;
    // A public no-argument constructor is needed for Jackson deserialization
    public RetailStore() {}

    protected RetailStore(Builder builder) {
        this.storeId = builder.storeId;
        this.storeName = builder.storeName;
        this.storeEmail = builder.storeEmail;
        this.passwordHash = builder.passwordHash;
        this.address = builder.address;
        this.contactPersons = builder.contactPersons != null ? builder.contactPersons : new ArrayList<>();
    }

    // Getters
    public String getStoreId() { return storeId; }
    public String getStoreName() { return storeName; }
    public String getStoreEmail() { return storeEmail; }
    public String getPasswordHash() { return passwordHash; }
    public Address getAddress() { return address; }
    public List<ContactPerson> getContactPersons() { return contactPersons; }
    
    // Helper methods for contact management
    public ContactPerson getFirstContactPerson() {
        return contactPersons.isEmpty() ? null : contactPersons.get(0);
    }
    
    public ContactPerson getSecondContactPerson() {
        return contactPersons.size() > 1 ? contactPersons.get(1) : null;
    }
    
    public void addContactPerson(ContactPerson contactPerson) {
        if (contactPerson != null) {
            this.contactPersons.add(contactPerson);
        }
    }

    // Public setters are required by Jackson
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContactPersons(List<ContactPerson> contactPersons) {
        this.contactPersons = contactPersons != null ? contactPersons : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "RetailStore{" +
                "storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeEmail='" + storeEmail + '\'' +
                ", address=" + address +
                ", contactPersons=" + contactPersons.size() + " contact persons" +
                '}';
    }

    public static class Builder {
        private String storeId;
        private String storeName;
        private String storeEmail;
        private String passwordHash;
        private Address address;
        private List<ContactPerson> contactPersons = new ArrayList<>();

        public Builder setStoreId(String storeId) {
            this.storeId = storeId;
            return this;
        }

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public Builder setStoreEmail(String storeEmail) {
            this.storeEmail = storeEmail;
            return this;
        }

        public Builder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder setContactPersons(List<ContactPerson> contactPersons) {
            this.contactPersons = contactPersons != null ? contactPersons : new ArrayList<>();
            return this;
        }

        public Builder addContactPerson(ContactPerson contactPerson) {
            if (contactPerson != null) {
                this.contactPersons.add(contactPerson);
            }
            return this;
        }

        public Builder copy(RetailStore retailStore) {
            this.storeId = retailStore.storeId;
            this.storeName = retailStore.storeName;
            this.storeEmail = retailStore.storeEmail;
            this.passwordHash = retailStore.passwordHash;
            this.address = retailStore.address;
            this.contactPersons = retailStore.contactPersons != null ? new ArrayList<>(retailStore.contactPersons) : new ArrayList<>();
            return this;
        }

        public RetailStore build() {
            return new RetailStore(this);
        }
    }
}
