package ac.za.cput.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Objects;

@Entity
@Table(name = "contact_person")
public class ContactPerson {
    @Id
    private String contactPersonId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "email_address", unique = true)
    private String emailAddress;
    
    @Column(name = "cell_phone_number")
    private String cellPhoneNumber;
    
    // Relationship with RetailStore
    @ManyToOne
    @JoinColumn(name = "retail_store_id", referencedColumnName = "storeId")
    @JsonBackReference("retailStore")
    private RetailStore retailStore;

    // A public no-argument constructor is needed for Jackson deserialization
    public ContactPerson() {}

    protected ContactPerson(Builder builder) {
        this.contactPersonId = builder.contactPersonId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.cellPhoneNumber = builder.cellPhoneNumber;
        this.retailStore = builder.retailStore;
    }

    // Getters
    public String getContactPersonId() { return contactPersonId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmailAddress() { return emailAddress; }
    public String getCellPhoneNumber() { return cellPhoneNumber; }
    public RetailStore getRetailStore() { return retailStore; }

    // Public setters are required by Jackson for deserialization
    public void setContactPersonId(String contactPersonId) { this.contactPersonId = contactPersonId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setCellPhoneNumber(String cellPhoneNumber) { this.cellPhoneNumber = cellPhoneNumber; }
    public void setRetailStore(RetailStore retailStore) { this.retailStore = retailStore; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactPerson that = (ContactPerson) o;
        return Objects.equals(contactPersonId, that.contactPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactPersonId);
    }

    @Override
    public String toString() {
        return "ContactPerson{" +
                "contactPersonId='" + contactPersonId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", cellPhoneNumber='" + cellPhoneNumber + '\'' +
                '}';
    }

    public static class Builder {
        private String contactPersonId;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String cellPhoneNumber;
        private RetailStore retailStore;

        public Builder setContactPersonId(String contactPersonId) {
            this.contactPersonId = contactPersonId;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder setCellPhoneNumber(String cellPhoneNumber) {
            this.cellPhoneNumber = cellPhoneNumber;
            return this;
        }

        public Builder setRetailStore(RetailStore retailStore) {
            this.retailStore = retailStore;
            return this;
        }

        public Builder copy(ContactPerson contactPerson) {
            this.contactPersonId = contactPerson.contactPersonId;
            this.firstName = contactPerson.firstName;
            this.lastName = contactPerson.lastName;
            this.emailAddress = contactPerson.emailAddress;
            this.cellPhoneNumber = contactPerson.cellPhoneNumber;
            this.retailStore = contactPerson.retailStore;
            return this;
        }

        public ContactPerson build() {
            return new ContactPerson(this);
        }
    }
}
