package ac.za.cput.service;

import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.domain.Enum.PaymentType;
import ac.za.cput.factory.PaymentMethodFactory;
import ac.za.cput.repository.PaymentMethodRepository;
import ac.za.cput.service.Imp.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentMethodService implements IPaymentMethodService {
    private final PaymentMethodRepository repository;

    @Autowired
    public PaymentMethodService(PaymentMethodRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaymentMethod create(PaymentMethod paymentMethod) { 
        // If paymentMethodId is not provided, use factory to create a new payment method
        if (paymentMethod.getPaymentMethodId() == null || paymentMethod.getPaymentMethodId().isEmpty()) {
            PaymentMethod newPaymentMethod = PaymentMethodFactory.createPaymentMethod(
                paymentMethod.getType(), 
                paymentMethod.getPaymentDate() != null ? paymentMethod.getPaymentDate() : LocalDateTime.now()
            );
            return this.repository.save(newPaymentMethod);
        }
        return this.repository.save(paymentMethod); 
    }

    @Override
    public PaymentMethod read(String paymentMethodId) { return this.repository.findById(paymentMethodId).orElse(null); }

    @Override
    public PaymentMethod update(PaymentMethod paymentMethod) { return this.repository.save(paymentMethod); }

    @Override
    public List<PaymentMethod> getAll() { return this.repository.findAll(); }
}