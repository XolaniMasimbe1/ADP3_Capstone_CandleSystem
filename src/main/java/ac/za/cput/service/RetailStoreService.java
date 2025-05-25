package ac.za.cput.service;

import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.RetailStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
