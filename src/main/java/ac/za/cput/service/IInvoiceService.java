package ac.za.cput.service;
/*
 * InvoiceService.java
 *
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Delivery;
import ac.za.cput.domain.Invoice;

import java.util.List;

public interface IInvoiceService extends IService<Invoice, String> {
    List<Invoice> getAll();
}
