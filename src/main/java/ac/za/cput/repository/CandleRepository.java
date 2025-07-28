package ac.za.cput.repository;

import ac.za.cput.domain.Candle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/
@Repository
public interface CandleRepository extends JpaRepository<Candle, String> {
    Optional<Candle> findByCandleNumber(String candleNumber);
    List<Candle> findByScent(String scent);
    List<Candle> findByPriceLessThanEqual(double maxPrice);
    List<Candle> findByPriceBetween(double minPrice, double maxPrice);
}