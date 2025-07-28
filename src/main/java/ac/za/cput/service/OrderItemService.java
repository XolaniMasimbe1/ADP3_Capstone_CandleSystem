package ac.za.cput.service;

import ac.za.cput.domain.OrderItem;
import ac.za.cput.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {
    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public OrderItem read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        if (repository.existsById(orderItem.getId())) {
            return repository.save(orderItem);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<OrderItem> getAll() {
        return repository.findAll();
    }

    @Override
    public List<OrderItem> findByOrderNumber(int orderNumber) {
        return repository.findByOrder_OrderNumber(orderNumber);
    }

    @Override
    public List<OrderItem> findByCandleNumber(String candleNumber) {
        return repository.findByCandle_CandleNumber(candleNumber);
    }

    @Override
    public double calculateSubtotal(int quantity, double unitPrice) {
        return quantity * unitPrice;
    }
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/