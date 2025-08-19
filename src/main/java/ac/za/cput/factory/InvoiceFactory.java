package ac.za.cput.factory;

import ac.za.cput.domain.Invoice;
import ac.za.cput.util.Helper;
import java.time.LocalDateTime;

public class InvoiceFactory {
    public static Invoice createInvoice(double totalAmount) {
        String invoiceNumber = Helper.generateId();
        return new Invoice.Builder()
                .setInvoiceNumber(invoiceNumber)
                .setTotalAmount(totalAmount)
                .build();
    }
}