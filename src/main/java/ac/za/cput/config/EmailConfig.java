package ac.za.cput.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    
    static {
        try {
            // Gmail SSL configuration - Use STARTTLS instead of SSL
            System.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
            System.setProperty("mail.smtp.ssl.checkserveridentity", "true");
            System.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
            System.setProperty("mail.smtp.ssl.enable", "false");
            System.setProperty("mail.smtp.starttls.enable", "true");
            System.setProperty("mail.smtp.starttls.required", "true");
            
            System.out.println("SSL configuration applied for Gmail");
        } catch (Exception e) {
            System.err.println("Error configuring SSL: " + e.getMessage());
        }
    }
}