package ac.za.cput.repository;

import ac.za.cput.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/*
 * OrderItemRepository.java
 * Repository for OrderItem
 * Author: [Your Name]
 * Student Number: [Your Student Number]
 * Date: [Today's Date]
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findById(Long id);
    List<OrderItem> findByOrder_OrderNumber(int orderNumber);
    List<OrderItem> findByCandle_CandleNumber(String candleNumber);
}