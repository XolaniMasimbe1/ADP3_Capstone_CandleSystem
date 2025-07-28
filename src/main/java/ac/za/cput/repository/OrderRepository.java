package ac.za.cput.repository;

import ac.za.cput.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/*
 * OrderRepository.java
 * Repository for Order
 * Author: [Your Name]
 * Student Number: [Your Student Number]
 * Date: [Today's Date]
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByOrderNumber(int orderNumber);
}