package ac.za.cput.service;

import ac.za.cput.domain.Order;
import ac.za.cput.repository.OrderRepository;
import ac.za.cput.service.Imp.IOrderService;
import ac.za.cput.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(Order order) {
        // Generate a unique ID before saving
        order.setOrderNumber(Helper.generateId());

        // Ensure the bidirectional relationship is correctly set on the server-side
        order.reestablishOrderItemsRelationship();

        return this.repository.save(order);
    }

    @Override
    public Order read(String orderNumber) { return this.repository.findById(orderNumber).orElse(null); }

    @Override
    public Order update(Order order) {
        Optional<Order> existingOrder = repository.findById(order.getOrderNumber());
        if (existingOrder.isPresent()) {
            Order managedOrder = existingOrder.get();
            managedOrder.setOrderStatus(order.getOrderStatus());

            return this.repository.save(managedOrder);
        }
        return null;
    }

    @Override
    public List<Order> getAll() { return this.repository.findAll(); }

    @Override
    public List<Order> getOrdersByStoreNumber(String storeNumber) {
        return repository.findByRetailStore_StoreNumber(storeNumber);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return repository.findByOrderNumber(orderNumber);
    }
}