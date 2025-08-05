package ac.za.cput.service;

import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;
import ac.za.cput.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    public Order read(Integer orderNumber) {
        return repository.findById(orderNumber).orElse(null);
    }

    @Override
    public Order update(Order order) {
        if (repository.existsById(order.getOrderNumber())) {
            return repository.save(order);
        }
        return null;
    }

    @Override
    public boolean delete(Integer orderNumber) {
        if (repository.existsById(orderNumber)) {
            repository.deleteById(orderNumber);
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Order> findByStatus(String status) {
        return repository.findByStatus(status);
    }

    @Override
    public double calculateOrderTotal(int orderNumber) {
        Order order = read(orderNumber);
        return order != null ? Double.parseDouble(order.getTotalAmount()) : 0.0;
    }

    @Override
    public Order addOrderItemToOrder(int orderNumber, OrderItem orderItem) {
        Order order = read(orderNumber);
        if (order != null) {
           // order.getOrderItems().add(orderItem);
            return repository.save(order);
        }
        return null;
    }
}
/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/