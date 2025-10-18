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
        String plainContent = buildOrderConfirmationPlainText(order);
        helper.setText(plainContent, htmlContent);
        
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
        html.append("<!DOCTYPE html>");
        html.append("<html><body style='margin:0;padding:0;background:#ffffff;'>");
        html.append("<table role='presentation' cellpadding='0' cellspacing='0' border='0' width='100%'>");
        html.append("<tr><td align='center' style='padding:16px;'>");
        html.append("<table role='presentation' cellpadding='0' cellspacing='0' border='0' width='600' style='width:600px;max-width:600px;border:1px solid #e5e7eb;border-radius:8px;background:#ffffff;font-family:Arial,Helvetica,sans-serif;color:#111111;'>");
        html.append("<tr><td align='center' style='padding:20px 20px 12px 20px;border-bottom:2px solid #e74c3c;'>");
        html.append("<div style='font-size:24px;font-weight:700;color:#e74c3c;'>Candle System</div>");
        html.append("<div style='font-size:18px;font-weight:600;color:#111111;margin-top:4px;'>Order Confirmation</div>");
        html.append("</td></tr>");
        html.append("<tr><td style='padding:16px 20px;'>");
        html.append("<div style='font-size:14px;line-height:1.6;color:#111111;'>");
        html.append("<div><strong>Order Number:</strong> ").append(order.getOrderNumber()).append("</div>");
        html.append("<div><strong>Invoice Number:</strong> ").append(order.getInvoice() != null ? order.getInvoice().getInvoiceNumber() : "N/A").append("</div>");
        html.append("<div><strong>Order Date:</strong> ").append(order.getOrderDate() != null ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) : "Not specified").append("</div>");
        html.append("<div><strong>Status:</strong> ").append(order.getOrderStatus()).append("</div>");
        html.append("<div><strong>Store:</strong> ").append(order.getRetailStore() != null ? order.getRetailStore().getStoreName() : "N/A").append("</div>");
        html.append("</div>");
        html.append("</td></tr>");
        html.append("<tr><td style='padding:16px 20px;'>");
        html.append("<table role='presentation' cellpadding='0' cellspacing='0' border='0' width='100%' style='background:#111827;color:#ffffff;border-radius:6px;'>");
        html.append("<tr><td align='center' style='padding:14px;'>");
        html.append("<div style='font-size:16px;'>Order Total</div>");
        html.append("<div style='font-size:22px;font-weight:700;margin-top:6px;'>R").append(String.format("%.2f", order.getInvoice() != null ? order.getInvoice().getTotalAmount() : 0.0)).append("</div>");
        html.append("</td></tr></table>");
        html.append("</td></tr>");
        html.append("<tr><td align='center' style='padding:20px;color:#6b7280;font-size:12px;font-family:Arial,Helvetica,sans-serif;'>");
        html.append("This is an automated confirmation email from Candle System. Thank you for your order.");
        html.append("</td></tr>");
        html.append("</table>");
        html.append("</td></tr></table>");
        html.append("</body></html>");
        return html.toString();
    }

    private String buildOrderConfirmationPlainText(Order order) {
        StringBuilder txt = new StringBuilder();
        txt.append("Candle System - Order Confirmation\n");
        txt.append("Order Number: ").append(order.getOrderNumber()).append("\n");
        txt.append("Invoice Number: ").append(order.getInvoice() != null ? order.getInvoice().getInvoiceNumber() : "N/A").append("\n");
        txt.append("Order Date: ").append(order.getOrderDate() != null ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) : "Not specified").append("\n");
        txt.append("Status: ").append(order.getOrderStatus()).append("\n");
        txt.append("Store: ").append(order.getRetailStore() != null ? order.getRetailStore().getStoreName() : "N/A").append("\n");
        txt.append("Order Total: R").append(String.format("%.2f", order.getInvoice() != null ? order.getInvoice().getTotalAmount() : 0.0)).append("\n");
        txt.append("Thank you for your order!\n");
        return txt.toString();
    }
}
