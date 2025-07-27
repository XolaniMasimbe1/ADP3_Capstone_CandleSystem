package ac.za.cput.service;
/*
 * InvoiceService.java
 * Service for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.repository.InoviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService implements IInvoiceService {
    private InoviceRepository repository;

    @Autowired
    public InvoiceService(InoviceRepository repository) {
        this.repository = repository;
    }
    @Override
    public Invoice create(Invoice invoice) {
      return  this .repository.save(invoice);
    }

    @Override
    public Invoice read(String invoiceNumber) {
        return this.repository.findById(invoiceNumber).orElse(null);
    }

    @Override
    public Invoice update(Invoice invoice) {
        return this.repository.save(invoice);
    }
    public Optional<Invoice> findById(String invoiceNumber) {
        return this.repository.findById(invoiceNumber);
    }

    @Override
    public List<Invoice> getAll() {
        return this.repository.findAll();
    }
}
