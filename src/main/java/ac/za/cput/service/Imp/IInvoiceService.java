
package ac.za.cput.service.Imp;
/*
 * InvoiceService.java
 *
 * Author: Siphosenkosi Mbala
 * Student Number: 221140700
 * Date: 19 August 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IInvoiceService extends IService<Invoice, String> {
    List<Invoice> getAll();
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
}
