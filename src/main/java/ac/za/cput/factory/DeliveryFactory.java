package ac.za.cput.factory;

import ac.za.cput.domain.Delivery;
import ac.za.cput.util.Helper;

import java.time.LocalDate;


public class DeliveryFactory {
    public static Delivery createDelivery(String status) {
        if (Helper.isNullOrEmpty(status) ) {
            return null;
        }
        String deliveryNumber = Helper.generateId();
        return new Delivery.Builder()
                .setDeliveryNumber(deliveryNumber)
                .setStatus(status)
                .build();
    }
}