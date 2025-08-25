package ac.za.cput.factory;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.PaymentMethod;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class PaymentMethodFactoryTest {

    private static PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(PaymentType.CASH,
            LocalDateTime.now());

    @Test
    void createPaymentMethod() {
        assertNotNull(paymentMethod);
        System.out.println(paymentMethod);
    }
}