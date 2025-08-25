package ac.za.cput.service;

import ac.za.cput.domain.Invoice;
import ac.za.cput.factory.InvoiceFactory;
import ac.za.cput.repository.InvoiceRepository;
import ac.za.cput.service.Imp.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {
    private final InvoiceRepository repository;

    @Autowired
    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Invoice create(Invoice invoice) { 
        // If invoiceNumber is not provided, use factory to create a new invoice
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().isEmpty()) {
            Invoice newInvoice = InvoiceFactory.createInvoice(invoice.getTotalAmount());
            return this.repository.save(newInvoice);
        }
        return this.repository.save(invoice); 
    }

    @Override
    public Invoice read(String invoiceNumber) { return this.repository.findById(invoiceNumber).orElse(null); }

    @Override
    public Invoice update(Invoice invoice) { return this.repository.save(invoice); }

    @Override
    public List<Invoice> getAll() { return this.repository.findAll(); }

    @Override
    public Optional<Invoice> findByInvoiceNumber(String invoiceNumber) {
        return this.repository.findByInvoiceNumber(invoiceNumber);
    }
}