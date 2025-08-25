package ac.za.cput.service.Imp;
/*
 * InvoiceService.java
 *
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IInvoiceService extends IService<Invoice, String> {
    List<Invoice> getAll();
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}