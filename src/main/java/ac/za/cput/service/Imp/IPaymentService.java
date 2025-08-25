package ac.za.cput.service.Imp;

import ac.za.cput.domain.Payment;
import ac.za.cput.service.IService;

import java.util.List;

public interface IPaymentService extends IService<Payment, String> {
    List<Payment> getAll();
}
