
package ac.za.cput.factory;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.Enum.BankName;
import ac.za.cput.domain.Payment;
import ac.za.cput.domain.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class PaymentFactoryTest {

    private static PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(PaymentType.CASH,
            LocalDateTime.now());
    private static Payment payment = PaymentFactory.createPayment(100.0, paymentMethod);

    @Test
    void createPayment() {
        assertNotNull(payment);
        System.out.println(payment);
    }

    @Test
    void createCardPayment() {
        PaymentMethod cardPaymentMethod = PaymentMethodFactory.createPaymentMethod(
                PaymentType.CREDIT_CARD, 
                LocalDateTime.now()
        );
        
        Payment cardPayment = PaymentFactory.createCardPayment(
                150.0, 
                cardPaymentMethod, 
                "1234567890123456", 
                "John Doe", 
                "12/25", 
                "123", 
                BankName.ABSA
        );
        
        assertNotNull(cardPayment);
        assertEquals(150.0, cardPayment.getTotalAmount());
        assertEquals("1234567890123456", cardPayment.getCardNumber());
        assertEquals("John Doe", cardPayment.getNameOnCard());
        assertEquals("12/25", cardPayment.getExpiryDate());
        assertEquals("123", cardPayment.getCvv());
        assertEquals(BankName.ABSA, cardPayment.getBank());
        assertEquals(PaymentType.CREDIT_CARD, cardPayment.getPaymentMethod().getType());
        
        System.out.println("Card Payment: " + cardPayment);
    }

    @Test
    void createCardPaymentWithInvalidData() {
        PaymentMethod cardPaymentMethod = PaymentMethodFactory.createPaymentMethod(
                PaymentType.CREDIT_CARD, 
                LocalDateTime.now()
        );
        
        // Test with null card number
        Payment invalidPayment1 = PaymentFactory.createCardPayment(
                150.0, 
                cardPaymentMethod, 
                null, 
                "John Doe", 
                "12/25", 
                "123", 
                BankName.ABSA
        );
        assertNull(invalidPayment1);
        
        // Test with empty name
        Payment invalidPayment2 = PaymentFactory.createCardPayment(
                150.0, 
                cardPaymentMethod, 
                "1234567890123456", 
                "", 
                "12/25", 
                "123", 
                BankName.ABSA
        );
        assertNull(invalidPayment2);
        
        // Test with null bank
        Payment invalidPayment3 = PaymentFactory.createCardPayment(
                150.0, 
                cardPaymentMethod, 
                "1234567890123456", 
                "John Doe", 
                "12/25", 
                "123", 
                null
        );
        assertNull(invalidPayment3);
        
        System.out.println("Invalid card payments correctly returned null");
    }
}
