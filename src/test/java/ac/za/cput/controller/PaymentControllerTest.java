package ac.za.cput.controller;

import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.domain.Payment;
import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.factory.PaymentFactory;
import ac.za.cput.factory.PaymentMethodFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    private static final String PAYMENT_URL = "/payment";
    private static final String PAYMENTMETHOD_URL = "/payment-method";

    private static Payment payment;
    private static PaymentMethod paymentMethod;

    @BeforeAll
    void setUp() {

        paymentMethod = PaymentMethodFactory.createPaymentMethod(PaymentType.CASH, LocalDateTime.now());

        payment = PaymentFactory.createPayment(100.0, paymentMethod);
    }

    @Test
    @Order(1)
    void a_create() {

        ResponseEntity<PaymentMethod> paymentMethodResponse = restTemplate.postForEntity(PAYMENTMETHOD_URL + "/create", paymentMethod, PaymentMethod.class);
        assertEquals(HttpStatus.OK, paymentMethodResponse.getStatusCode());
        assertNotNull(paymentMethodResponse.getBody());
        paymentMethod = paymentMethodResponse.getBody();
        System.out.println("Created PaymentMethod: " + paymentMethod);


        payment = new Payment.Builder().copy(payment).setPaymentMethod(paymentMethod).build();


        ResponseEntity<Payment> paymentResponse = restTemplate.postForEntity(PAYMENT_URL + "/create", payment, Payment.class);
        assertEquals(HttpStatus.OK, paymentResponse.getStatusCode());
        assertNotNull(paymentResponse.getBody());
        payment = paymentResponse.getBody();
        System.out.println("Created Payment: " + payment);
    }

    @Test
    @Order(2)
    void b_read() {

        assertNotNull(payment.getPaymentNumber());

        String url = PAYMENT_URL + "/read/" + payment.getPaymentNumber();
        ResponseEntity<Payment> response = restTemplate.getForEntity(url, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(payment.getPaymentNumber(), response.getBody().getPaymentNumber());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {

        Payment updatedPayment = new Payment.Builder()
                .copy(payment)
                .setTotalAmount(150.0)
                .build();


        restTemplate.put(PAYMENT_URL + "/update", updatedPayment);


        String readUrl = PAYMENT_URL + "/read/" + updatedPayment.getPaymentNumber();
        ResponseEntity<Payment> response = restTemplate.getForEntity(readUrl, Payment.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(150.0, response.getBody().getTotalAmount());
        System.out.println("Updated: " + response.getBody());
    }

    @Test
    @Order(4)
    void d_getAll() {
        String url = PAYMENT_URL + "/all";
        ResponseEntity<Payment[]> response = restTemplate.getForEntity(url, Payment[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Payments:");
        for (Payment p : response.getBody()) {
            System.out.println(p);
        }
    }
}