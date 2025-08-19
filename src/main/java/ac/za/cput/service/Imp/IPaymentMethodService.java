package ac.za.cput.service.Imp;

import ac.za.cput.domain.PaymentMethod;
import ac.za.cput.service.IService;

import java.util.List;

public interface IPaymentMethodService extends IService<PaymentMethod, String> {
    List<PaymentMethod> getAll();
}