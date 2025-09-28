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
    
    @Column(name = "store_number", unique = true)
    private String storeNumber;
    
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
    @OrderBy("createdAt ASC")
    @JsonManagedReference
    private List<Contact> contacts = new ArrayList<>();
    

    // A public no-argument constructor is needed for Jackson deserialization
    public RetailStore() {}

    protected RetailStore(Builder builder) {
        this.storeId = builder.storeId;
        this.storeNumber = builder.storeNumber;
        this.storeName = builder.storeName;
        this.storeEmail = builder.storeEmail;
        this.passwordHash = builder.passwordHash;
        this.address = builder.address;
        this.contacts = builder.contacts != null ? builder.contacts : new ArrayList<>();
    }

    // Getters
    public String getStoreId() { return storeId; }
    public String getStoreNumber() { return storeNumber; }
    public String getStoreName() { return storeName; }
    public String getStoreEmail() { return storeEmail; }
    public String getPasswordHash() { return passwordHash; }
    public Address getAddress() { return address; }
    public List<Contact> getContacts() { return contacts; }
    
    // Helper methods for contact management
    public Contact getFirstContact() {
        return contacts.isEmpty() ? null : contacts.get(0);
    }
    
    public Contact getSecondContact() {
        return contacts.size() > 1 ? contacts.get(1) : null;
    }
    
    public void addContact(Contact contact) {
        if (contact != null) {
            contact.setRetailStore(this);
            this.contacts.add(contact);
        }
    }

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

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts != null ? contacts : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "RetailStore{" +
                "storeId='" + storeId + '\'' +
                ", storeNumber='" + storeNumber + '\'' +
                ", storeName='" + storeName + '\'' +
                ", storeEmail='" + storeEmail + '\'' +
                ", address=" + address +
                ", contacts=" + contacts.size() + " contacts" +
                '}';
    }

    public static class Builder {
        private String storeId;
        private String storeNumber;
        private String storeName;
        private String storeEmail;
        private String passwordHash;
        private Address address;
        private List<Contact> contacts = new ArrayList<>();

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

        public Builder setContacts(List<Contact> contacts) {
            this.contacts = contacts != null ? contacts : new ArrayList<>();
            return this;
        }

        public Builder addContact(Contact contact) {
            if (contact != null) {
                this.contacts.add(contact);
            }
            return this;
        }

        public Builder copy(RetailStore retailStore) {
            this.storeId = retailStore.storeId;
            this.storeNumber = retailStore.storeNumber;
            this.storeName = retailStore.storeName;
            this.storeEmail = retailStore.storeEmail;
            this.passwordHash = retailStore.passwordHash;
            this.address = retailStore.address;
            this.contacts = retailStore.contacts != null ? new ArrayList<>(retailStore.contacts) : new ArrayList<>();
            return this;
        }

        public RetailStore build() {
            return new RetailStore(this);
        }
    }
}
