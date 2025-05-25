package ac.za.cput.factory;

import ac.za.cput.domain.Delivery;
import ac.za.cput.util.Helper;
import java.util.Date;

/*
 * DeliveryFactory.java
 * Factory for Delivery
 * Author: Anda Matomela
 * Date: 26 May 2025
 **/
public class DeliveryFactory {
    public static Delivery createDelivery(Date deliveryDate, String deliveryStatus, String driverName) {
        if (Helper.isNullOrEmpty(deliveryStatus) || Helper.isNullOrEmpty(driverName) || deliveryDate == null) {
            return null;
        }
        int deliveryNumber = Helper.generateIntId(); // Assume Helper generates an int ID
        String trackingNumber = "TRK-" + Helper.generateId();

        return new Delivery.Builder()
                .setDeliveryNumber(deliveryNumber)
                .setDeliveryDate(deliveryDate)
                .setDeliveryStatus(deliveryStatus)
                .setDriverName(driverName)
                .setTrackingNumber(trackingNumber)
                .build();
    }
}