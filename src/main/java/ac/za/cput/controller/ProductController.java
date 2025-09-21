package ac.za.cput.controller;

import ac.za.cput.domain.Product;
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

            // Create upload directory if it doesn't exist
            String uploadDir = "uploads/images/";
            java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
            if (!java.nio.file.Files.exists(uploadPath)) {
                java.nio.file.Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = productNumber + "_" + System.currentTimeMillis() + fileExtension;
            
            // Save file to disk
            java.nio.file.Path filePath = uploadPath.resolve(uniqueFilename);
            java.nio.file.Files.write(filePath, file.getBytes());

            // Update product with image path (not binary data)
            String imagePath = uploadDir + uniqueFilename;
            product = new Product.Builder()
                    .copy(product)
                    .setImageData(imagePath.getBytes()) // Store path as bytes for now
                    .build();

            service.update(product);
            
            return ResponseEntity.ok("Image uploaded successfully. File path: " + imagePath);
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

            // Get image path from stored data
            String imagePath = new String(product.getImageData());
            java.nio.file.Path filePath = java.nio.file.Paths.get(imagePath);
            
            if (!java.nio.file.Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            // Read file from disk
            byte[] imageBytes = java.nio.file.Files.readAllBytes(filePath);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(imageBytes.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (IOException e) {
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

            // Delete file from disk if it exists
            if (product.getImageData() != null) {
                String imagePath = new String(product.getImageData());
                java.nio.file.Path filePath = java.nio.file.Paths.get(imagePath);
                if (java.nio.file.Files.exists(filePath)) {
                    java.nio.file.Files.delete(filePath);
                }
            }

            // Remove image path from product
            product = new Product.Builder()
                    .copy(product)
                    .setImageData(null)
                    .build();

            service.update(product);
            return ResponseEntity.ok("Image deleted successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting image: " + e.getMessage());
        }
    }
}