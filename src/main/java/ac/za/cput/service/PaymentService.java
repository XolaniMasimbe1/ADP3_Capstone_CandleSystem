package ac.za.cput.service;

import ac.za.cput.domain.Payment;
import ac.za.cput.factory.PaymentFactory;
import ac.za.cput.repository.PaymentRepository;
import ac.za.cput.service.Imp.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    private final PaymentRepository repository;

    @Autowired
    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment create(Payment payment) { 
        // If paymentNumber is not provided, use factory to create a new payment
        if (payment.getPaymentNumber() == null || payment.getPaymentNumber().isEmpty()) {
            Payment newPayment = PaymentFactory.createPayment(payment.getTotalAmount(), payment.getPaymentMethod());
            return this.repository.save(newPayment);
        }
        return this.repository.save(payment); 
    }

    @Override
    public Payment read(String paymentNumber) { return this.repository.findById(paymentNumber).orElse(null); }

    @Override
    public Payment update(Payment payment) { return this.repository.save(payment); }

    @Override
    public List<Payment> getAll() { return this.repository.findAll(); }
}