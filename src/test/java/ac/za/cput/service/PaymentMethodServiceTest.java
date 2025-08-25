package ac.za.cput.service;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.factory.PaymentMethodFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentMethodServiceTest {

    @Autowired
    private PaymentMethodService paymentMethodService;

    private static PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(
            PaymentType.CASH, LocalDateTime.now());

    @Test
    @Order(1)
    void create() {
        PaymentMethod createdPaymentMethod = paymentMethodService.create(paymentMethod);
        assertNotNull(createdPaymentMethod);
        System.out.println("Created Payment Method: " + createdPaymentMethod);
    }

    @Test
    @Order(2)
    void read() {
        PaymentMethod readPaymentMethod = paymentMethodService.read(paymentMethod.getPaymentMethodId());
        assertNotNull(readPaymentMethod);
        System.out.println("Read Payment Method: " + readPaymentMethod);
    }

    @Test
    @Order(3)
    void update() {
        PaymentMethod updatedPaymentMethod = new PaymentMethod.Builder()
                .setPaymentMethodId(paymentMethod.getPaymentMethodId())
                .setType(PaymentType.CREDIT_CARD)
                .setPaymentDate(LocalDateTime.now())
                .build();

        PaymentMethod result = paymentMethodService.update(updatedPaymentMethod);
        assertNotNull(result);
        System.out.println("Updated Payment Method: " + result);
    }

    @Test
    @Order(4)
    void getAll() {
        assertFalse(paymentMethodService.getAll().isEmpty(), "Payment Method list should not be empty");
        System.out.println("All Payment Methods: " + paymentMethodService.getAll());
    }
}