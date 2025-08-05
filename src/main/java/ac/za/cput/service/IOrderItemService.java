package ac.za.cput.service;

import ac.za.cput.domain.OrderItem;
import java.util.List;

public interface IOrderItemService extends IService<OrderItem, Long> {
    OrderItem create(OrderItem orderItem);
    OrderItem read(Long id);
    OrderItem update(OrderItem orderItem);
    boolean delete(Long id);
    List<OrderItem> getAll();
    List<OrderItem> findByOrderNumber(int orderNumber);
    List<OrderItem> findByCandleNumber(String candleNumber);
    double calculateSubtotal(int quantity, double unitPrice);
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/