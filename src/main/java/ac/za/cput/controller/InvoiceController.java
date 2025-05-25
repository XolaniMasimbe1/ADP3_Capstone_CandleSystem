package ac.za.cput.controller;
/*
 * InvoiceCotroller.java
 * Test for Invoice
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.Invoice;
import ac.za.cput.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/CandleSystem/invoice")
public class InvoiceController {

    private final InvoiceService inovoiceservice;

    @Autowired
    public InvoiceController(InvoiceService inovoiceservice) {
        this.inovoiceservice = inovoiceservice;
    }
    @PostMapping("/create")
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        Invoice created = inovoiceservice.create(invoice);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/read/{invoiceNumber}")
    public ResponseEntity<Invoice> read(@PathVariable String invoiceNumber) {
        return inovoiceservice.findById(invoiceNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public Invoice update(@RequestBody Invoice invoice) {
        return inovoiceservice.update(invoice);
    }
    @GetMapping("/find/{invoiceNumber}")
    public ResponseEntity<Invoice> findByInvoiceNumber(@PathVariable String invoiceNumber) {
        return inovoiceservice.findById(invoiceNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
