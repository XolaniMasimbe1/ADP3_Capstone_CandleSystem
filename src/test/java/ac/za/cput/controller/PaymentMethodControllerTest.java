package ac.za.cput.controller;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.domain.User;
import ac.za.cput.factory.PaymentMethodFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PaymentMethodControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String PAYMENTMETHOD_URL ="/payment-method";
    private static PaymentMethod paymentMethod;


    @BeforeAll
    static void setUp() {
        paymentMethod = PaymentMethodFactory.createPaymentMethod(PaymentType.CASH, LocalDateTime.now());
    }

    @Test
    void create() {
        ResponseEntity<PaymentMethod> response = restTemplate.postForEntity(PAYMENTMETHOD_URL +"/create", paymentMethod, PaymentMethod.class);
        assertNotNull(response);
        System.out.println("Created Payment Method: " + response.getBody());
    }

    @Test
    void read() {
        ResponseEntity<PaymentMethod> response = restTemplate.getForEntity(PAYMENTMETHOD_URL + "/read/"
                + paymentMethod.getPaymentMethodId(), PaymentMethod.class);
        assertNotNull(response);
        System.out.println("Read Payment Method: " + response.getBody());
    }

    @Test
    void update() {
        PaymentMethod updatedPaymentMethod = new PaymentMethod.Builder()
                .copy(paymentMethod)
                .setType(PaymentType.EFT)
                .build();
        restTemplate.put(PAYMENTMETHOD_URL + "/" + paymentMethod.getPaymentMethodId(), updatedPaymentMethod);
        ResponseEntity<PaymentMethod> response = restTemplate.getForEntity(PAYMENTMETHOD_URL + "/update" + paymentMethod.getPaymentMethodId(), PaymentMethod.class);
        assertNotNull(response);
        System.out.println("Updated Payment Method: " + response.getBody());
    }

    @Test
    void getAll() {
        ResponseEntity<String> response = restTemplate.getForEntity(PAYMENTMETHOD_URL + "/all", String.class);
        assertNotNull(response);
        System.out.println("All Payment Methods: " + response.getBody());
    }
}