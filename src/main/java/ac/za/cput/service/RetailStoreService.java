package ac.za.cput.service;
/*
 * RetailStoreService.java
 * Service for RetailStore
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 **/
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.RetailStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetailStoreService implements IRetailStoreService {
    private RetailStoreRepository repository;
    @Autowired
    public RetailStoreService(RetailStoreRepository repository) {
        this.repository = repository;
    }
    @Override
    public RetailStore create(RetailStore retailStore) {
        return this.repository.save(retailStore);
    }

    @Override
    public RetailStore read(String storeNumber) {
        return this.repository.findById(storeNumber).orElse(null);
    }

    @Override
    public RetailStore update(RetailStore retailStore) {
        return this.repository.save(retailStore);
    }

    public Optional<RetailStore> findByStoreNumber(String storeNumber) {
        return this.repository.findByStoreNumber(storeNumber);
    }

    @Override
    public List<RetailStore> getAll() {
        return this.repository.findAll();
    }
}
