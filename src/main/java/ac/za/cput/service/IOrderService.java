package ac.za.cput.service;

import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;

import java.util.List;

public interface IOrderService extends IService<Order, Integer> {
    Order create(Order order);
    Order read(Integer orderNumber);
    Order update(Order order);
    boolean delete(Integer orderNumber);
    List<Order> getAll();
    List<Order> findByStatus(String status);
    double calculateOrderTotal(int orderNumber);
    Order addOrderItemToOrder(int orderNumber, OrderItem orderItem);
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/