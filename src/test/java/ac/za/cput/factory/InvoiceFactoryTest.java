package ac.za.cput.factory;

import ac.za.cput.domain.Invoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvoiceFactoryTest {

    private static Invoice invoice = InvoiceFactory.createInvoice(250.75);

    @Test
    void createInvoice() {
        assertNotNull(invoice);
        System.out.println("Created Invoice: " + invoice);
    }
}