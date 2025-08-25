package ac.za.cput.service.Imp;

import ac.za.cput.domain.Order;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IOrderService extends IService<Order, String> {
    List<Order> getAll();
    List<Order> getOrdersByStoreNumber(String storeNumber);
    Optional<Order> findByOrderNumber(String orderNumber);
}
