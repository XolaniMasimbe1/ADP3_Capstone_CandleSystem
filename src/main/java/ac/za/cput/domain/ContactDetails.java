package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contact_details")
public class ContactDetails {
    @Id
    private String contactId;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    private String postalCode;
    private String street;
    private String city;
    private String province;
    private String country;

    // A public no-argument constructor is needed for Jackson deserialization
    public ContactDetails() {}

    protected ContactDetails(Builder builder) {
        this.contactId = builder.contactId;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.postalCode = builder.postalCode;
        this.street = builder.street;
        this.city = builder.city;
        this.province = builder.province;
        this.country = builder.country;
    }

    // Getters
    public String getContactId() { return contactId; }
    
    public String getEmail() {
        return email;
    }

    // Public setters are required by Jackson for deserialization
    public void setContactId(String contactId) { this.contactId = contactId; }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactDetails that = (ContactDetails) o;
        return Objects.equals(contactId, that.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }

    @Override
    public String toString() {
        return "ContactDetails{" +
                "contactId='" + contactId + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", postalCode=" + postalCode +
                ", street=" + street +
                ", city=" + city +
                ", province=" + province +
                ", country=" + country +
                '}';
    }

    public static class Builder {
        private String contactId;
        private String email;
        private String phoneNumber;
        private String postalCode;
        private String street;
        private String city;
        private String province;
        private String country;

        public Builder setContactId(String contactId) {
            this.contactId = contactId;
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

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder copy(ContactDetails contactDetails) {
            this.contactId = contactDetails.contactId;
            this.email = contactDetails.email;
            this.phoneNumber = contactDetails.phoneNumber;
            this.postalCode = contactDetails.postalCode;
            this.street = contactDetails.street;
            this.city = contactDetails.city;
            this.province = contactDetails.province;
            this.country = contactDetails.country;
            return this;
        }

        public ContactDetails build() {
            return new ContactDetails(this);
        }
    }
}