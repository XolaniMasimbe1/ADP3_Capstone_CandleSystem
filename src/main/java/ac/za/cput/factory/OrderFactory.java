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

    private static final String[] VALID_PAYMENT_METHODS = {"Credit Card", "Debit Card",  "EFT"};

    private static final double TAX_RATE = 0.15;

    public static Order createOrder(String status, double totalAmount,
                                    String paymentMethod) {

        if (Helper.isNullOrEmpty(status) || Helper.isNullOrEmpty(paymentMethod) ||
                totalAmount <= 0) {
            return null;
        }


        if (!Helper.isValidPaymentMethod(paymentMethod, VALID_PAYMENT_METHODS)) {
            return null;
        }


        double taxAmount = totalAmount * TAX_RATE;

        int orderNumber = Integer.parseInt(Helper.generateNumericId(6));
        LocalDate orderDate = LocalDate.now();

        return new Order.Builder()
                .setOrderNumber(orderNumber)
                .setOrderDate(orderDate)
                .setStatus(status)
                .setTotalAmount(String.valueOf(totalAmount))
                .setTaxAmount(taxAmount)
                .setPaymentMethod(paymentMethod)
                .build();
    }
}