package ac.za.cput.service;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.Payment;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.factory.PaymentFactory;
import ac.za.cput.factory.PaymentMethodFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    private static PaymentMethod paymentMethod = PaymentMethodFactory.createPaymentMethod(PaymentType.CASH,
            LocalDateTime.now());
    private static Payment payment = PaymentFactory.createPayment(100.0, paymentMethod);
    @Test
    void create() {
        paymentMethodService.create(paymentMethod);
        Payment createdPayment = paymentService.create(payment);
        assertNotNull(createdPayment);
        System.out.println("Created Payment: " + createdPayment);
    }

    @Test
    void read() {
        Payment readPayment = paymentService.read(payment.getPaymentNumber());
        assertNotNull(readPayment);
        System.out.println("Read Payment: " + readPayment);
    }

    @Test
    void update() {
        Payment updatedPayment = new Payment.Builder()
                .setPaymentNumber(payment.getPaymentNumber())
                .setTotalAmount(1500.0)
                .setPaymentMethod(paymentMethod)
                .build();

        Payment result = paymentService.update(updatedPayment);
        assertNotNull(result);
        System.out.println("Updated Payment: " + result);
    }

    @Test
    void getAll() {
        assertFalse(paymentService.getAll().isEmpty(), "Payment list should not be empty");
        System.out.println("All Payments: " + paymentService.getAll());
    }
}