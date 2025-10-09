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
    private final OrderConfirmationEmailService emailService;

    @Autowired
    public OrderService(OrderRepository repository, OrderConfirmationEmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public Order create(Order order) {
        // Generate a unique ID before saving
        order.setOrderNumber(Helper.generateId());
        
        // Set the order date to current date if not already set
        if (order.getOrderDate() == null) {
            order.setOrderDate(java.time.LocalDate.now());
        }

        // Ensure the bidirectional relationship is correctly set on the server-side
        order.reestablishOrderItemsRelationship();

        // Save the order first
        Order savedOrder = this.repository.save(order);
        
        // Send confirmation email asynchronously
        try {
            // Fetch the order with all product images for email
            Order orderWithImages = this.getOrderWithImages(savedOrder.getOrderNumber());
            emailService.sendOrderConfirmationEmail(orderWithImages);
            System.out.println("Order confirmation email sent successfully for order: " + savedOrder.getOrderNumber());
        } catch (Exception e) {
            // Log the error but don't fail the order creation
            System.err.println("Failed to send order confirmation email for order " + savedOrder.getOrderNumber() + ": " + e.getMessage());
            System.err.println("Order was created successfully but email notification failed.");
        }
        
        return savedOrder;
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
    public List<Order> getOrdersByStoreId(String storeId) {
        return repository.findByRetailStore_StoreId(storeId);
    }

    @Override
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return repository.findByOrderNumber(orderNumber);
    }

    // Get order with all product images loaded
    public Order getOrderWithImages(String orderNumber) {
        return repository.findById(orderNumber).orElse(null);
    }
}