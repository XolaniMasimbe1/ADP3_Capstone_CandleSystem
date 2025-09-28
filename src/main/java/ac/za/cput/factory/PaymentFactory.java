package ac.za.cput.factory;

import ac.za.cput.domain.Payment;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.domain.Enum.BankName;
import ac.za.cput.util.Helper;

public class PaymentFactory {
    public static Payment createPayment(double totalAmount, PaymentMethod paymentMethod) {
        if (totalAmount < 0 || paymentMethod == null) {
            return null;
        }
        String paymentNumber = Helper.generateId();
        return new Payment.Builder()
                .setPaymentNumber(paymentNumber)
                .setTotalAmount(totalAmount)
                .setPaymentMethod(paymentMethod)
                .build();
    }

    public static Payment createCardPayment(double totalAmount, PaymentMethod paymentMethod, 
                                          String cardNumber, String nameOnCard, String expiryDate, String cvv, 
                                          BankName bank) {
        if (totalAmount < 0 || paymentMethod == null || 
            Helper.isNullOrEmpty(cardNumber) || Helper.isNullOrEmpty(nameOnCard) ||
            Helper.isNullOrEmpty(expiryDate) || Helper.isNullOrEmpty(cvv) || bank == null) {
            return null;
        }
        
        String paymentNumber = Helper.generateId();
        return new Payment.Builder()
                .setPaymentNumber(paymentNumber)
                .setTotalAmount(totalAmount)
                .setCardNumber(cardNumber)
                .setNameOnCard(nameOnCard)
                .setExpiryDate(expiryDate)
                .setCvv(cvv)
                .setBank(bank)
                .setPaymentMethod(paymentMethod)
                .build();
    }
}