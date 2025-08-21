package ac.za.cput.factory;

import ac.za.cput.domain.Enum.PaymentType;
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
}