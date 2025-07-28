package ac.za.cput.service;

import ac.za.cput.domain.Candle;
import java.util.List;

public interface ICandleService extends IService<Candle, String> {
    Candle create(Candle candle);
    Candle read(String candleNumber);
    Candle update(Candle candle);
    boolean delete(String candleNumber);
    List<Candle> getAll();
    List<Candle> findByScent(String scent);
    List<Candle> findByPriceRange(double minPrice, double maxPrice);
    int checkStockQuantity(String candleNumber);
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/