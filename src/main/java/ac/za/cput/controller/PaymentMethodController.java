package ac.za.cput.controller;

import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/payment-method")
public class PaymentMethodController {
    private final PaymentMethodService service;

    @Autowired
    public PaymentMethodController(PaymentMethodService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public PaymentMethod create(@RequestBody PaymentMethod paymentMethod) {
        return service.create(paymentMethod);
    }

    @GetMapping("/read/{paymentMethodId}")
    public PaymentMethod read(@PathVariable String paymentMethodId) {
        return service.read(paymentMethodId);
    }

    @PutMapping("/update")
    public PaymentMethod update(@RequestBody PaymentMethod paymentMethod) {
        return service.update(paymentMethod);
    }

    @GetMapping("/all")
    public List<PaymentMethod> getAll() {
        return service.getAll();
    }
}