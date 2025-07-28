package ac.za.cput.service;

import ac.za.cput.domain.Candle;
import ac.za.cput.repository.CandleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandleService implements ICandleService {
    private final CandleRepository repository;

    public CandleService(CandleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Candle create(Candle candle) {
        return repository.save(candle);
    }

    @Override
    public Candle read(String candleNumber) {
        return repository.findById(candleNumber).orElse(null);
    }

    @Override
    public Candle update(Candle candle) {
        if (repository.existsById(candle.getCandleNumber())) {
            return repository.save(candle);
        }
        return null;
    }

    @Override
    public boolean delete(String candleNumber) {
        if (repository.existsById(candleNumber)) {
            repository.deleteById(candleNumber);
            return true;
        }
        return false;
    }

    @Override
    public List<Candle> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Candle> findByScent(String scent) {
        return repository.findByScent(scent);
    }

    @Override
    public List<Candle> findByPriceRange(double minPrice, double maxPrice) {
        return repository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public int checkStockQuantity(String candleNumber) {
        Candle candle = read(candleNumber);
        return candle != null ? candle.getStockQuantity() : 0;
    }
}

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/