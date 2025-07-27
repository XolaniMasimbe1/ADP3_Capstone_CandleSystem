package ac.za.cput.service;
/*
 * InvoiceServiceTest.java
 * Service for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.factory.InvoiceFactory;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvoiceServiceTest {
    @Autowired
    private InvoiceService invoiceService;
    private static Invoice invoice = InvoiceFactory.createInvoice("2023-10-01", "1000.00");

    @Test
    void create() {
        Invoice createdInvoice = invoiceService.create(invoice);
        assertNotNull(createdInvoice);
        System.out.println("Created Invoice: " + createdInvoice);

    }

    @Test
    void read() {
        Invoice foundInvoice = invoiceService.read(invoice.getInvoiceNumber());
        assertNotNull(foundInvoice);
        System.out.println("Retrieved Invoice: " + foundInvoice);
    }

    @Test
    void update() {
        Invoice updatedInvoice = new Invoice.Builder()
                .copy(invoice)
                .setInvoiceAmount("1500.00")
                .build();
        Invoice savedInvoice = invoiceService.update(updatedInvoice);
        assertNotNull(savedInvoice);
        System.out.println("Updated Invoice: " + savedInvoice);
    }

    @Test
    void findById() {
        Invoice foundInvoice = invoiceService.findById(invoice.getInvoiceNumber()).orElse(null);
        assertNotNull(foundInvoice);
        System.out.println("Found Invoice: " + foundInvoice);
    }
    @Test
    void getAll() {
        assertFalse(invoiceService.getAll().isEmpty());
        System.out.println("All Invoices: " + invoiceService.getAll());
    }
}