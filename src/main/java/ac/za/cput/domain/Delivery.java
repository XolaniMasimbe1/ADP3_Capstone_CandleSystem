package ac.za.cput.domain;

import java.util.Date;

/*
 * Delivery.java
 * Domain for Delivery
 * Author: Anda Matomela
 * Date: 26 May 2025
 **/
public class Delivery {
    private int deliveryNumber;
    private Date deliveryDate;
    private String deliveryStatus;
    private String driverName;
    private String trackingNumber;


    private Delivery(Builder builder) {
        this.deliveryNumber = builder.deliveryNumber;
        this.deliveryDate = builder.deliveryDate;
        this.deliveryStatus = builder.deliveryStatus;
        this.driverName = builder.driverName;
        this.trackingNumber = builder.trackingNumber;
    }


    public int getDeliveryNumber() { return deliveryNumber; }
    public Date getDeliveryDate() { return deliveryDate; }
    public String getDeliveryStatus() { return deliveryStatus; }
    public String getDriverName() { return driverName; }
    public String getTrackingNumber() { return trackingNumber; }


    public static class Builder {
        private int deliveryNumber;
        private Date deliveryDate;
        private String deliveryStatus;
        private String driverName;
        private String trackingNumber;

        public Builder setDeliveryNumber(int deliveryNumber) {
            this.deliveryNumber = deliveryNumber;
            return this;
        }

        public Builder setDeliveryDate(Date deliveryDate) {
            this.deliveryDate = deliveryDate;
            return this;
        }

        public Builder setDeliveryStatus(String deliveryStatus) {
            this.deliveryStatus = deliveryStatus;
            return this;
        }

        public Builder setDriverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public Builder setTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public Delivery build() {
            return new Delivery(this);
        }
    }
}