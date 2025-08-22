package ac.za.cput.service;

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

    private static Invoice invoice = InvoiceFactory.createInvoice(250.75);

    @Test
    void create() {
        Invoice createdInvoice = invoiceService.create(invoice);
        assertNotNull(createdInvoice);
        System.out.println("Created Invoice: " + createdInvoice);
    }

    @Test
    void read() {
        Invoice readInvoice = invoiceService.read(invoice.getInvoiceNumber());
        assertNotNull(readInvoice);
        System.out.println("Read Invoice: " + readInvoice);
    }

    @Test
    void update() {
        Invoice updatedInvoice = new Invoice.Builder()
                .setInvoiceNumber(invoice.getInvoiceNumber())
                .setTotalAmount(6000.00)
                .build();

        Invoice result = invoiceService.update(updatedInvoice);
        assertNotNull(result);
        System.out.println("Updated Invoice: " + result);
    }

    @Test
    void getAll() {
        assertNotNull(invoiceService.getAll());
        System.out.println("All Invoices: " + invoiceService.getAll());
    }
}