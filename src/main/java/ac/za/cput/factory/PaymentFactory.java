package ac.za.cput.factory;

import ac.za.cput.domain.Payment;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.util.Helper;
import java.time.LocalDateTime;

public class PaymentFactory {
    public static Payment createPayment(double totalAmount,  PaymentMethod paymentMethod) {
        if (totalAmount < 0  || paymentMethod == null) {
            return null;
        }
        String paymentNumber = Helper.generateId();
        return new Payment.Builder()
                .setPaymentNumber(paymentNumber)
                .setTotalAmount(totalAmount)
                .setPaymentMethod(paymentMethod)
                .build();
    }
}