package ac.za.cput.service;

import ac.za.cput.domain.OrderItem;
import ac.za.cput.repository.OrderItemRepository;
import ac.za.cput.service.Imp.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {
    private final OrderItemRepository repository;

    @Autowired
    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public OrderItem create(OrderItem orderItem) { return this.repository.save(orderItem); }

    @Override
    public OrderItem read(Long id) { return this.repository.findById(id).orElse(null); }

    @Override
    public OrderItem update(OrderItem orderItem) { return this.repository.save(orderItem); }

    @Override
    public List<OrderItem> getAll() { return this.repository.findAll(); }
}