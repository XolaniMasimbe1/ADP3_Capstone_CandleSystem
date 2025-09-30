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
import ac.za.cput.service.Imp.IRetailStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RetailStoreService implements IRetailStoreService {
    private final RetailStoreRepository repository;

    @Autowired
    public RetailStoreService(RetailStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public RetailStore create(RetailStore retailStore) { return this.repository.save(retailStore); }

    @Override
    public RetailStore read(String storeNumber) { return this.repository.findByStoreNumber(storeNumber).orElse(null); }

    public RetailStore readById(String storeId) { return this.repository.findById(storeId).orElse(null); }

    @Override
    public RetailStore update(RetailStore retailStore) { return this.repository.save(retailStore); }

    @Override
    public List<RetailStore> getAll() { return this.repository.findAll(); }

    @Override
    public Optional<RetailStore> findByStoreNumber(String storeNumber) {
        return this.repository.findByStoreNumber(storeNumber);
    }


    public Optional<RetailStore> findByStoreEmail(String storeEmail) {
        return this.repository.findByStoreEmail(storeEmail);
    }
}