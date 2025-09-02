package ac.za.cput.controller;

import ac.za.cput.domain.Product;
import ac.za.cput.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
@Transactional
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Product create(@RequestBody Product product) {
        return service.create(product);
    }

    @GetMapping("/read/{productNumber}")
    public Product read(@PathVariable String productNumber) {
        return service.read(productNumber);
    }

    @PutMapping("/update")
    public Product update(@RequestBody Product product) {
        return service.update(product);
    }

    @GetMapping("/find/{productNumber}")
    public Optional<Product> findByProductNumber(@PathVariable String productNumber) {
        return service.findByProductNumber(productNumber);
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return service.getAll();
    }
}