package ac.za.cput.service.Imp;


import ac.za.cput.domain.Product;
import ac.za.cput.service.IService;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IService<Product, String> {
    List<Product> getAll();
    Optional<Product> findByProductNumber(String productNumber);
}
