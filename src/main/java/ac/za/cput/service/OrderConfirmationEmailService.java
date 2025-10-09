package ac.za.cput.service;

import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;
import ac.za.cput.domain.Product;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Order Confirmation Email Service
 * 
 * This service handles sending order confirmation emails to retail stores
 * when they place orders. The email includes:
 * - Order number and invoice number
 * - Product details with images
 * - Order total and payment information
 * 
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 26 June 2025
 */
@Service
public class OrderConfirmationEmailService {
    
    private final JavaMailSender mailSender;
    private final ProductRepository productRepository;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Autowired
    public OrderConfirmationEmailService(JavaMailSender mailSender, ProductRepository productRepository) {
        this.mailSender = mailSender;
        this.productRepository = productRepository;
    }
    
    /**
     * Send order confirmation email to retail store
     * 
     * @param order The order that was created
     * @throws MessagingException if email sending fails
     */
    public void sendOrderConfirmationEmail(Order order) throws MessagingException {
        if (order == null || order.getRetailStore() == null) {
            throw new IllegalArgumentException("Order and retail store must not be null");
        }
        
        RetailStore store = order.getRetailStore();
        String storeEmail = store.getStoreEmail();
        
        System.out.println("OrderConfirmationEmailService - Store details:");
        System.out.println("  Store ID: " + (store != null ? store.getStoreId() : "NULL"));
        System.out.println("  Store Name: " + (store != null ? store.getStoreName() : "NULL"));
        System.out.println("  Store Email: " + storeEmail);
        
        if (storeEmail == null || storeEmail.trim().isEmpty()) {
            System.err.println("ERROR: Store email is missing for store: " + (store != null ? store.getStoreName() : "Unknown"));
            System.err.println("Order will be created but no email will be sent.");
            
            // Instead of throwing an exception, just log the issue and return
            // This allows the order to be created successfully even without email
            System.err.println("Skipping email notification due to missing store email.");
            return;
        }
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom(fromEmail);
        helper.setTo(storeEmail);
        helper.setSubject("Order Confirmation - " + order.getOrderNumber());
        
        String htmlContent = buildOrderConfirmationHtml(order);
        helper.setText(htmlContent, true);
        
        mailSender.send(message);
    }
    
    /**
     * Create a professional product placeholder for email
     * 
     * @param productName Product name
     * @return HTML for product placeholder
     */
    private String createProductPlaceholder(String productName) {
        // Create a simple, email-friendly placeholder
        String initial = productName != null && !productName.isEmpty() ? 
            String.valueOf(productName.charAt(0)).toUpperCase() : "?";
        
        return "<div style='width: 80px; height: 80px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); " +
               "display: flex; align-items: center; justify-content: center; color: white; font-weight: bold; " +
               "border-radius: 8px; margin-right: 15px; flex-shrink: 0; font-size: 24px;'>" + initial + "</div>";
    }

    /**
     * Check if image is suitable for email (not too large)
     * 
     * @param imageData Image data bytes
     * @return true if image is suitable for email
     */
    private boolean isImageSuitableForEmail(byte[] imageData) {
        // Limit to 50KB for email compatibility
        return imageData != null && imageData.length > 0 && imageData.length <= 50000;
    }

    /**
     * Build HTML content for order confirmation email
     * 
     * @param order The order to create email content for
     * @return HTML string for the email
     */
    private String buildOrderConfirmationHtml(Order order) {
        StringBuilder html = new StringBuilder();
        
        // Email header
        html.append("<!DOCTYPE html>");
        html.append("<html><head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>Order Confirmation</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; margin: 0; padding: 20px; background-color: #f4f4f4; }");
        html.append(".container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        html.append(".header { text-align: center; border-bottom: 2px solid #e74c3c; padding-bottom: 20px; margin-bottom: 30px; }");
        html.append(".header h1 { color: #e74c3c; margin: 0; font-size: 28px; }");
        html.append(".order-info { background: #f8f9fa; padding: 15px; border-radius: 5px; margin: 20px 0; }");
        html.append(".order-info h3 { margin-top: 0; color: #2c3e50; }");
        html.append(".product-item { display: flex; align-items: center; padding: 15px; border: 1px solid #ddd; border-radius: 5px; margin: 10px 0; background: white; }");
        html.append(".product-image { width: 80px; height: 80px; object-fit: cover; border-radius: 5px; margin-right: 15px; display: block; flex-shrink: 0; }");
        html.append(".product-details { flex: 1; }");
        html.append(".product-name { font-weight: bold; color: #2c3e50; margin-bottom: 5px; }");
        html.append(".product-info { color: #666; font-size: 14px; margin: 2px 0; }");
        html.append(".quantity-price { display: flex; justify-content: space-between; align-items: center; margin-top: 10px; }");
        html.append(".quantity { background: #e74c3c; color: white; padding: 5px 10px; border-radius: 15px; font-size: 12px; }");
        html.append(".price { font-weight: bold; color: #27ae60; font-size: 16px; }");
        html.append(".total-section { background: #2c3e50; color: white; padding: 20px; border-radius: 5px; margin-top: 20px; text-align: center; }");
        html.append(".total-amount { font-size: 24px; font-weight: bold; margin: 10px 0; }");
        html.append(".footer { text-align: center; margin-top: 30px; padding-top: 20px; border-top: 1px solid #ddd; color: #666; }");
        html.append("</style>");
        html.append("</head><body>");
        
        // Container start
        html.append("<div class='container'>");
        
        // Header
        html.append("<div class='header'>");
        html.append("<h1>🕯️ Candle System</h1>");
        html.append("<h2>Order Confirmation</h2>");
        html.append("</div>");
        
        // Order information
        html.append("<div class='order-info'>");
        html.append("<h3>📋 Order Details</h3>");
        html.append("<p><strong>Order Number:</strong> ").append(order.getOrderNumber()).append("</p>");
        html.append("<p><strong>Invoice Number:</strong> ").append(order.getInvoice() != null ? order.getInvoice().getInvoiceNumber() : "N/A").append("</p>");
        html.append("<p><strong>Order Date:</strong> ").append(
            order.getOrderDate() != null ? 
            order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) : 
            "Not specified"
        ).append("</p>");
        html.append("<p><strong>Status:</strong> ").append(order.getOrderStatus()).append("</p>");
        html.append("<p><strong>Store:</strong> ").append(order.getRetailStore().getStoreName()).append("</p>");
        html.append("</div>");
        
        // Products section
        html.append("<h3>🛍️ Ordered Products</h3>");
        
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                
                // Fetch the product with images from database to ensure we have the image data
                Product productWithImages = productRepository.findById(product.getProductNumber()).orElse(product);
                
                html.append("<div class='product-item'>");
                
                // Product image
                System.out.println("Processing product: " + productWithImages.getName());
                System.out.println("Product image data: " + (productWithImages.getImageData() != null ? "Present (" + productWithImages.getImageData().length + " bytes)" : "NULL"));
                
                // Display actual product image from database
                if (productWithImages.getImageData() != null && productWithImages.getImageData().length > 0) {
                    if (isImageSuitableForEmail(productWithImages.getImageData())) {
                        try {
                            // Convert image data to base64 for email embedding
                            String base64Image = Base64.getEncoder().encodeToString(productWithImages.getImageData());
                            System.out.println("Product image found: " + productWithImages.getName() + " (" + productWithImages.getImageData().length + " bytes) - SUITABLE FOR EMAIL");
                            
                            // Create proper img tag with base64 data
                            html.append("<img src='data:image/jpeg;base64,")
                                .append(base64Image)
                                .append("' style='width: 80px; height: 80px; object-fit: cover; border-radius: 8px; margin-right: 15px; flex-shrink: 0;' alt='")
                                .append(productWithImages.getName())
                                .append("'>");
                        } catch (Exception e) {
                            System.err.println("Error processing image for product " + productWithImages.getName() + ": " + e.getMessage());
                            // Fallback to placeholder if image processing fails
                            html.append(createProductPlaceholder(productWithImages.getName()));
                        }
                    } else {
                        System.out.println("Product image too large for email: " + productWithImages.getName() + " (" + productWithImages.getImageData().length + " bytes) - USING PLACEHOLDER");
                        // Use placeholder for large images
                        html.append(createProductPlaceholder(productWithImages.getName()));
                    }
                } else {
                    System.out.println("No image data for product: " + productWithImages.getName());
                    // Use placeholder only if no image exists
                    html.append(createProductPlaceholder(productWithImages.getName()));
                }
                
                // Product details
                html.append("<div class='product-details'>");
                html.append("<div class='product-name'>").append(productWithImages.getName()).append("</div>");
                html.append("<div class='product-info'>Scent: ").append(productWithImages.getScent() != null ? productWithImages.getScent() : "N/A").append("</div>");
                html.append("<div class='product-info'>Color: ").append(productWithImages.getColor() != null ? productWithImages.getColor() : "N/A").append("</div>");
                html.append("<div class='product-info'>Size: ").append(productWithImages.getSize() != null ? productWithImages.getSize() : "N/A").append("</div>");
                html.append("<div class='product-info'>Manufacturer: ").append(productWithImages.getManufacturer() != null ? productWithImages.getManufacturer().getManufacturerName() : "N/A").append("</div>");
                
                html.append("<div class='quantity-price'>");
                html.append("<span class='quantity'>Qty: ").append(item.getQuantity()).append("</span>");
                html.append("<span class='price'>R").append(String.format("%.2f", item.getUnitPrice() * item.getQuantity())).append("</span>");
                html.append("</div>");
                
                html.append("</div>");
                html.append("</div>");
            }
        } else {
            html.append("<p>No products found in this order.</p>");
        }
        
        // Total section
        html.append("<div class='total-section'>");
        html.append("<h3>💰 Order Total</h3>");
        html.append("<div class='total-amount'>R").append(String.format("%.2f", order.getInvoice() != null ? order.getInvoice().getTotalAmount() : 0.0)).append("</div>");
        html.append("<p>Thank you for your order! We'll process it shortly.</p>");
        html.append("</div>");
        
        // Footer
        html.append("<div class='footer'>");
        html.append("<p>This is an automated confirmation email from Candle System.</p>");
        html.append("<p>Product images are displayed above. If you have any questions, please contact our support team.</p>");
        html.append("</div>");
        
        // Container end
        html.append("</div>");
        html.append("</body></html>");
        
        return html.toString();
    }
}
