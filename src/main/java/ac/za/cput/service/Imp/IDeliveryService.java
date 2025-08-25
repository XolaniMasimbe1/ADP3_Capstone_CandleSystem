package ac.za.cput.service.Imp;

import ac.za.cput.domain.Delivery;
import ac.za.cput.service.IService;

import java.util.List;

public interface IDeliveryService extends IService<Delivery, String> {
    List<Delivery> getAll();
}