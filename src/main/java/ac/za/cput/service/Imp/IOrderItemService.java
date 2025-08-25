package ac.za.cput.service.Imp;

import ac.za.cput.domain.OrderItem;
import ac.za.cput.service.IService;

import java.util.List;


public interface IOrderItemService extends IService<OrderItem, Long> {
    List<OrderItem> getAll();
}
