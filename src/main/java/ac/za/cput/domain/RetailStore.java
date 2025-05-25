package ac.za.cput.domain;

import jakarta.persistence.*;
/*
 * RetailStore.java
 * Pojo for RetailStore
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 May 2025
 **/

@Entity
public class RetailStore {
    @Id
    private String storeNumber;
    private String storeName;
    private String contactPerson;


    @Embedded
    private ContactDetails contactDetails;

    protected RetailStore(){

    }
    public RetailStore(Builder builder) {
        this.storeNumber = builder.storeNumber;
        this.storeName = builder.storeName;
        this.contactPerson = builder.contactPerson;
        this.contactDetails = builder.contactDetails;
    }

    public String getStoreNumber() {
        return storeNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public String getStoreName() {
        return storeName;
    }
    @Override
    public String toString() {
        return "RetailStore{" +
                "storeNumber='" + storeNumber + '\'' +
                ", storeName='" + storeName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactDetails=" + contactDetails +
                '}';
    }
    public static class Builder {
        private String storeNumber;
        private String storeName;
        private String contactPerson;
        private ContactDetails contactDetails;

        public Builder setStoreNumber(String storeNumber) {
            this.storeNumber = storeNumber;
            return this;
        }

        public Builder setStoreName(String storeName) {
            this.storeName = storeName;
            return this;
        }

        public Builder setContactPerson(String contactPerson) {
            this.contactPerson = contactPerson;
            return this;
        }

        public Builder setContactDetails(ContactDetails contactDetails) {
            this.contactDetails = contactDetails;
            return this;
        }
       public Builder copy(RetailStore retailStore){
            this.storeNumber = retailStore.storeNumber;
            this.storeName = retailStore.storeName;
            this.contactPerson = retailStore.contactPerson;
            this.contactDetails = retailStore.contactDetails;
            return this;
       }

        public RetailStore build() {
            return new RetailStore(this);
        }
    }
}
