package ac.za.cput.controller;

import ac.za.cput.domain.Invoice;
import ac.za.cput.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService service;

    @Autowired
    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Invoice create(@RequestBody Invoice invoice) {
        return service.create(invoice);
    }

    @GetMapping("/read/{invoiceNumber}")
    public Invoice read(@PathVariable String invoiceNumber) {
        return service.read(invoiceNumber);
    }

    @PutMapping("/update")
    public Invoice update(@RequestBody Invoice invoice) {
        return service.update(invoice);
    }

    @GetMapping("/find/{invoiceNumber}")
    public Invoice findById(@PathVariable String invoiceNumber) {
        return service.read(invoiceNumber);
    }

    @GetMapping("/all")
    public List<Invoice> getAll() {
        return service.getAll();
    }
}
