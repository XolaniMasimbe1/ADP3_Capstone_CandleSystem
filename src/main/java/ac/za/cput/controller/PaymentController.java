package ac.za.cput.controller;

import ac.za.cput.domain.Payment;
import ac.za.cput.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payment")
@Transactional
public class PaymentController {
    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Payment create(@RequestBody Payment payment) {
        return service.create(payment);
    }

    @GetMapping("/read/{paymentNumber}")
    public Payment read(@PathVariable String paymentNumber) {
        return service.read(paymentNumber);
    }

    @PutMapping("/update")
    public Payment update(@RequestBody Payment payment) {
        return service.update(payment);
    }

    @GetMapping("/all")
    public List<Payment> getAll() {
        return service.getAll();
    }
}