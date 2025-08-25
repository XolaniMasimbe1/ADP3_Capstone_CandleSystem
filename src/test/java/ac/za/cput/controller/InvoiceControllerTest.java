package ac.za.cput.controller;

import ac.za.cput.domain.Invoice;
import ac.za.cput.factory.InvoiceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String INVOICE_URL = "/invoice";
    private static Invoice invoice;

    @BeforeAll
    static void setUp() {

        invoice = InvoiceFactory.createInvoice(900.00);
    }

    @Test
    void create() {
        ResponseEntity<Invoice> response = restTemplate.postForEntity(INVOICE_URL + "/create", invoice, Invoice.class);

        assertNotNull(response.getBody());
        System.out.println("Created: " + response.getBody());
    }

    @Test
    void read() {
        ResponseEntity<Invoice> response = restTemplate.getForEntity(
                INVOICE_URL + "/read/" + invoice.getInvoiceNumber(), Invoice.class);
        assertNotNull(response.getBody());
        System.out.println("Read: " + response.getBody());

    }

    @Test
    void update() {
        Invoice updated = new Invoice.Builder().copy(invoice).setTotalAmount(950.00).build();
        restTemplate.postForEntity(INVOICE_URL + "/update", updated, Invoice.class);
        ResponseEntity<Invoice> response = restTemplate.getForEntity(
                INVOICE_URL + "/read/" + updated.getInvoiceNumber(), Invoice.class);
        assertNotNull(response.getBody());
        System.out.println("Updated: " + response.getBody());
    }


    @Test
    void getAll() {
        ResponseEntity<Invoice[]> response =
                restTemplate.getForEntity(INVOICE_URL + "/all", Invoice[].class);
        assertNotNull(response.getBody());
        System.out.println("Get All: " + response.getBody());
    }
}