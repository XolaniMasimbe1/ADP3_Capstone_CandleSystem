package ac.za.cput.controller;
/*
 * InvoiceCotroller.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.repository.InoviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CandleSystem/invoice")
public class InvoiceController {

    private final InoviceRepository inoviceRepository;

    @Autowired
    public InvoiceController(InoviceRepository inoviceRepository) {
        this.inoviceRepository = inoviceRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        Invoice created = inoviceRepository.save(invoice);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{invoiceNumber}")
    public ResponseEntity<Invoice> read(@PathVariable String invoiceNumber) {
        return inoviceRepository.findById(invoiceNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<Invoice> update(@RequestBody Invoice invoice) {
        if (!inoviceRepository.existsById(invoice.getInvoiceNumber())) {
            return ResponseEntity.notFound().build();
        }
        Invoice updated = inoviceRepository.save(invoice);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{invoiceNumber}")
    public ResponseEntity<Void> delete(@PathVariable String invoiceNumber) {
        if (!inoviceRepository.existsById(invoiceNumber)) {
            return ResponseEntity.notFound().build();
        }
        inoviceRepository.deleteById(invoiceNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Invoice>> getAll() {
        List<Invoice> all = (List<Invoice>) inoviceRepository.findAll();
        return ResponseEntity.ok(all);
    }
}
