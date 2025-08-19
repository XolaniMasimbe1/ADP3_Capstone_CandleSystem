package ac.za.cput.service;

import ac.za.cput.domain.Product;
import ac.za.cput.repository.ProductRepository;
import ac.za.cput.service.Imp.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product create(Product product) { return this.repository.save(product); }

    @Override
    public Product read(String productNumber) { return this.repository.findById(productNumber).orElse(null); }

    @Override
    public Product update(Product product) { return this.repository.save(product); }

    @Override
    public List<Product> getAll() { return this.repository.findAll(); }

    @Override
    public Optional<Product> findByProductNumber(String productNumber) {
        return this.repository.findByProductNumber(productNumber);
    }
}