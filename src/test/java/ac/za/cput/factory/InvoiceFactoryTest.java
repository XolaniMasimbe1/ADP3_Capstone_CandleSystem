package ac.za.cput.factory;

import ac.za.cput.domain.Invoice;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvoiceFactoryTest {

    @Test
    void createInvoice() {
        Invoice invoice = InvoiceFactory.createInvoice( 550.75);
        assertNotNull(invoice);
        System.out.println("Created Invoice: " + invoice);
    }
}