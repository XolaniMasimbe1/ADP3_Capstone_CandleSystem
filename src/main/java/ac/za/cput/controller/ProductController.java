package ac.za.cput.controller;

import ac.za.cput.domain.Product;
import ac.za.cput.factory.ProductFactory;
import ac.za.cput.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        // Use ProductFactory to ensure productNumber is generated
        Product newProduct = ProductFactory.createProduct(
            product.getName(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getScent(),
            product.getColor(),
            product.getSize(),
            product.getImageData(),
            product.getManufacturer()
        );
        
        if (newProduct == null) {
            throw new RuntimeException("Failed to create product - invalid data provided");
        }
        
        return service.create(newProduct);
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

    @PostMapping("/upload-image/{productNumber}")
    public ResponseEntity<String> uploadImage(@PathVariable String productNumber, @RequestParam("image") MultipartFile file) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload");
            }

            // Check file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.badRequest().body("File must be an image (JPEG, PNG, GIF, etc.)");
            }

            // Check file size (max 5MB)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("File size must be less than 5MB");
            }

            // Find product
            Product product = service.read(productNumber);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Store the actual image bytes in the database
            byte[] imageBytes = file.getBytes();

            // Update product with actual image data
            product = new Product.Builder()
                    .copy(product)
                    .setImageData(imageBytes) // Store actual image bytes directly in DB
                    .build();

            service.update(product);

            return ResponseEntity.ok("Image uploaded successfully to database. Image size: " + imageBytes.length + " bytes");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image: " + e.getMessage());
        }
    }

    @GetMapping("/image/{productNumber}")
    public ResponseEntity<byte[]> getImage(@PathVariable String productNumber) {
        try {
            Product product = service.read(productNumber);
            if (product == null || product.getImageData() == null) {
                return ResponseEntity.notFound().build();
            }

            // Get image bytes directly from database
            byte[] imageBytes = product.getImageData();

            // Determine content type based on image data (you might want to store this separately)
            String contentType = determineContentType(imageBytes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentLength(imageBytes.length);
            headers.setCacheControl("max-age=3600"); // Cache for 1 hour

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/image/{productNumber}")
    public ResponseEntity<String> deleteImage(@PathVariable String productNumber) {
        try {
            Product product = service.read(productNumber);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Remove image data from product in database
            product = new Product.Builder()
                    .copy(product)
                    .setImageData(null)
                    .build();

            service.update(product);
            return ResponseEntity.ok("Image deleted successfully from database");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting image: " + e.getMessage());
        }
    }

    // Helper method to determine content type from image bytes
    private String determineContentType(byte[] imageBytes) {
        if (imageBytes.length < 4) return "application/octet-stream";

        // Check for PNG
        if (imageBytes[0] == (byte) 0x89 && imageBytes[1] == (byte) 0x50 &&
                imageBytes[2] == (byte) 0x4E && imageBytes[3] == (byte) 0x47) {
            return "image/png";
        }
        // Check for JPEG
        else if (imageBytes[0] == (byte) 0xFF && imageBytes[1] == (byte) 0xD8) {
            return "image/jpeg";
        }
        // Check for GIF
        else if (imageBytes[0] == (byte) 0x47 && imageBytes[1] == (byte) 0x49 &&
                imageBytes[2] == (byte) 0x46) {
            return "image/gif";
        }

        return "application/octet-stream";
    }
}