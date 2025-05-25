package ac.za.cput.factory;

import ac.za.cput.domain.Invoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/*
 * InvoiceFactoryTest.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 25 June 2025
 **/
class InvoiceFactoryTest {

    private  static Invoice invoice = InvoiceFactory.createInvoice("2023-10-01", "1000.00");
    @Test
    void createInvoice() {
        assertNotNull(invoice);
        System.out.println(invoice);
    }
}