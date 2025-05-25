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
/*
 * InvoiceControllerTest.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class InvoiceControllerTest {

    private static Invoice invoice;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:8080/CandleSystem/invoice";

    @BeforeAll
    public static void setUp() {
        invoice = InvoiceFactory.createInvoice("2023-10-01", "1000.00");
    }

    @Test
    void a_create() {
        String url = BASE_URL + "/create";
        ResponseEntity<Invoice> postResponse = restTemplate.postForEntity(url, invoice, Invoice.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        invoice = postResponse.getBody(); // save for later use
        System.out.println("Created: " + invoice);
    }

    @Test
    void b_read() {
        String url = BASE_URL + "/read/" + invoice.getInvoiceNumber(); // Use invoiceNumber, not date
        ResponseEntity<Invoice> response = restTemplate.getForEntity(url, Invoice.class);
        assertNotNull(response.getBody());
        assertEquals(invoice.getInvoiceNumber(), response.getBody().getInvoiceNumber());
        System.out.println("Read: " + response.getBody());
    }

    @Test
    void c_update() {
        Invoice updatedInvoice = new Invoice.Builder()
                .copy(invoice)
                .setInvoiceDate("2023-10-02")
                .setInvoiceAmount("1200.00")
                .build();
        String url = BASE_URL + "/update";
        ResponseEntity<Invoice> response = restTemplate.postForEntity(url, updatedInvoice, Invoice.class);
        assertNotNull(response.getBody());
        assertEquals("2023-10-02", response.getBody().getInvoiceDate());
        invoice = response.getBody(); // update saved invoice
        System.out.println("Updated: " + invoice);
    }

    @Test
    void d_getAll() {
        String url = BASE_URL + "/all";
        ResponseEntity<Invoice[]> response = restTemplate.getForEntity(url, Invoice[].class);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        System.out.println("All Invoices:");
        for (Invoice inv : response.getBody()) {
            System.out.println(inv);
        }
    }

    @Test
    void e_delete() {
        String url = BASE_URL + "/delete/" + invoice.getInvoiceNumber(); // use correct ID
        restTemplate.delete(url);
        ResponseEntity<Invoice> response = restTemplate.getForEntity(BASE_URL + "/read/" + invoice.getInvoiceNumber(), Invoice.class);
        assertNull(response.getBody());
        System.out.println("Deleted Invoice: " + invoice.getInvoiceNumber());
    }
}
