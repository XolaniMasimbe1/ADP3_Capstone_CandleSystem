package ac.za.cput.factory;
/*
 * InvoiceFactory.java
 * Factory for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 May 2025
 **/
import ac.za.cput.domain.ContactDetails;
import ac.za.cput.domain.Invoice;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.util.Helper;

public class InvoiceFactory {
    public static Invoice createInvoice( String invoiceDate, String invoiceAmount) {
        if ( Helper.isNullOrEmpty(invoiceDate) || Helper.isNullOrEmpty(invoiceAmount)) {
            return null;
        }
        String invoiceNumber = Helper.generateId();
      return new Invoice.Builder()
                .setInvoiceNumber(invoiceNumber)
                .setInvoiceDate(invoiceDate)
                .setInvoiceAmount(invoiceAmount)
                .build();
    }

}
