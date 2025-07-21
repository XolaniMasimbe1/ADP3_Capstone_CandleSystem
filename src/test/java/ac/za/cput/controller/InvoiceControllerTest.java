package ac.za.cput.controller;

/*
 * InvoiceControllerTest.java
 * Test for InvoiceController (create, read, update, findById only)
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 20 July 2025
 */

import ac.za.cput.domain.Invoice;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.factory.InvoiceFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceControllerTest {

    private static final String BASE_URL = "http://localhost:8080/CandleSystem/invoice";

    private static Invoice invoice;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public static void setUp() {
        invoice = InvoiceFactory.createInvoice("2023-10-01", "1000.00");
    }

    @Test
    @Order(1)
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Invoice> postResponse = restTemplate.postForEntity(url, invoice, Invoice.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        invoice = postResponse.getBody();
        System.out.println("Created: " + invoice);
    }

    @Test
    @Order(2)
    void b_read() {
        String url = BASE_URL + "/read/" + invoice.getInvoiceNumber();
        ResponseEntity<Invoice> response = restTemplate.getForEntity(url, Invoice.class);
        assertNotNull(response.getBody());
        assertEquals(invoice.getInvoiceNumber(), response.getBody().getInvoiceNumber());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    @Order(3)
    void c_update() {
        Invoice updatedInvoice = new Invoice.Builder()
                .copy(invoice)
                .setInvoiceDate("2023-10-02")
                .setInvoiceAmount("1200.00")
                .build();

        String url = BASE_URL + "/update";

        restTemplate.put(url, updatedInvoice);


        ResponseEntity<Invoice> response = restTemplate.getForEntity(BASE_URL + "/read/" + updatedInvoice.getInvoiceNumber(), Invoice.class);

        assertNotNull(response.getBody());
        assertEquals("2023-10-02", response.getBody().getInvoiceDate());
        invoice = response.getBody();
        System.out.println("Updated: " + invoice);
    }


    @Test
    @Order(4)
    void d_findById() {
        String url = BASE_URL + "/find/" + invoice.getInvoiceNumber();
        ResponseEntity<Invoice> response = restTemplate.getForEntity(url, Invoice.class);
        assertNotNull(response.getBody());
        assertEquals(invoice.getInvoiceNumber(), response.getBody().getInvoiceNumber());
        System.out.println("Found by ID: " + response.getBody());
    }

    @Test
    @Order(5)
    void getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Invoice[]> response = restTemplate.getForEntity(url, Invoice[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Invoice: ");
        for (Invoice store : response.getBody()) {
            System.out.println(store);
        }
    }
}


