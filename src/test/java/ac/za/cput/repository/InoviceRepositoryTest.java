package ac.za.cput.repository;
/*
 * InvoiceRepositoryTest.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 June 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.factory.InvoiceFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InoviceRepositoryTest {
    // Assuming you have a repository instance injected or created here
    @Autowired
    private  InoviceRepository inoviceRepository;


    private static Invoice invoice = InvoiceFactory.createInvoice("2023-10-01", "1000.00");

    @Test
    @Order(1)
    void createInvoice() {
        Invoice createsInvoice = inoviceRepository.save(invoice);
        assertNotNull(createsInvoice);
        System.out.println("Created Invoice: " + createsInvoice);
    }
    @Test
    @Order(2)
    void readInvoice() {
        Invoice readInvoice = inoviceRepository.findById(invoice.getInvoiceNumber()).orElse(null);
        assertNotNull(readInvoice);
        System.out.println("Read Invoice: " + readInvoice);
    }
    @Test
    @Order(3)
    void updateInvoice() {
        Invoice newInvoice = new Invoice.Builder()
                .copy(invoice)
                .setInvoiceAmount("1500.00")
                .build();
       Invoice savedInvoice = inoviceRepository.save(newInvoice);
        assertNotNull(savedInvoice);
        System.out.println("Updated Invoice: " + savedInvoice);
    }
    @Test
    @Order(4)
    @Disabled
    void deleteInvoice() {
      inoviceRepository.deleteById(invoice.getInvoiceNumber());
      assertFalse(inoviceRepository.existsById(invoice.getInvoiceNumber()));
        System.out.println("Deleted Invoice with ID: " + invoice.getInvoiceNumber());

    }
    @Test
    void findById() {
        Invoice foundInvoice = inoviceRepository.findById(invoice.getInvoiceNumber()).orElse(null);
        assertNotNull(foundInvoice);
        System.out.println("Found Invoice: " + foundInvoice);
    }
}