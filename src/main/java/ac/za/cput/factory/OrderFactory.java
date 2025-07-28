package ac.za.cput.factory;

import ac.za.cput.domain.Order;
import ac.za.cput.util.Helper;
import java.time.LocalDate;

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/
public class OrderFactory {
    public static Order createOrder(String status, double totalAmount,
                                    double taxAmount, String paymentMethod) {

        if (Helper.isNullOrEmpty(status) || Helper.isNullOrEmpty(paymentMethod) ||
                totalAmount <= 0 || taxAmount < 0) {
            return null;
        }

        int orderNumber = Integer.parseInt(Helper.generateNumericId(6));
        LocalDate orderDate = LocalDate.now();

        return new Order.Builder()
                .setOrderNumber(orderNumber)
                .setOrderDate(orderDate)
                .setStatus(status)
                //.setTotalAmount(totalAmount)
                //.setTaxAmount(taxAmount)
                //.setPaymentMethod(paymentMethod)
                .build();
    }
}