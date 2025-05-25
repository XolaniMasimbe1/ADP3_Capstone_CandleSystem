package ac.za.cput.domain;

import jakarta.persistence.Embeddable;
/*
 * ContactDetails.java
 *
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
@Embeddable
public class ContactDetails {

    private String email;
    private String phoneNumber;
    private String postalCode;
    private String street;
    private String city;
    private String province;
    private String country;

    public ContactDetails() {
    }
    public ContactDetails(String email, String phoneNumber, String postalCode, String street, String city, String province, String country) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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


}
