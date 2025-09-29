package ac.za.cput.domain;

import ac.za.cput.domain.Enum.Province;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {
    @Id
    private String addressId;
    
    @Column(name = "street_number")
    private String streetNumber;
    
    @Column(name = "street_name")
    private String streetName;
    
    @Column(name = "suburb")
    private String suburb;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "province")
    private Province province;
    
    @Column(name = "country")
    private String country;

    // A public no-argument constructor is needed for Jackson deserialization
    public Address() {}

    protected Address(Builder builder) {
        this.addressId = builder.addressId;
        this.streetNumber = builder.streetNumber;
        this.streetName = builder.streetName;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
        this.province = builder.province;
        this.country = builder.country;
    }

    // Getters
    public String getAddressId() { return addressId; }
    public String getStreetNumber() { return streetNumber; }
    public String getStreetName() { return streetName; }
    public String getSuburb() { return suburb; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public Province getProvince() { return province; }
    public String getCountry() { return country; }

    // Public setters are required by Jackson for deserialization
    public void setAddressId(String addressId) { this.addressId = addressId; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }
    public void setStreetName(String streetName) { this.streetName = streetName; }
    public void setSuburb(String suburb) { this.suburb = suburb; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setProvince(Province province) { this.province = province; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(addressId, address.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId='" + addressId + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", streetName='" + streetName + '\'' +
                ", suburb='" + suburb + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", province=" + province +
                ", country='" + country + '\'' +
                '}';
    }

    public static class Builder {
        private String addressId;
        private String streetNumber;
        private String streetName;
        private String suburb;
        private String city;
        private String postalCode;
        private Province province;
        private String country;

        public Builder setAddressId(String addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder setStreetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setSuburb(String suburb) {
            this.suburb = suburb;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setProvince(Province province) {
            this.province = province;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder copy(Address address) {
            this.addressId = address.addressId;
            this.streetNumber = address.streetNumber;
            this.streetName = address.streetName;
            this.suburb = address.suburb;
            this.city = address.city;
            this.postalCode = address.postalCode;
            this.province = address.province;
            this.country = address.country;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
