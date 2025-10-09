package ac.za.cput.config;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.AdminRepository;
import ac.za.cput.repository.RetailStoreRepository;
import ac.za.cput.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Filter to check if user account is still active
 * This prevents blocked users from accessing the system even with valid JWT tokens
 */
@Component
public class UserStatusFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private RetailStoreRepository retailStoreRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip check for public endpoints
        if (request.getServletPath().contains("/auth") || 
            request.getServletPath().contains("/register") ||
            request.getServletPath().contains("/product") ||
            request.getServletPath().contains("/manufacture") ||
            request.getServletPath().contains("/store/provinces")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            String userEmail = authentication.getName();
            
            try {
                // Check if user is still active
                boolean isActive = checkUserStatus(userEmail);
                
                if (!isActive) {
                    // User is blocked, clear authentication and return 403
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json");
                    response.getWriter().write("{\"error\": \"Account is blocked. Please contact administrator.\"}");
                    return;
                }
            } catch (Exception e) {
                // If we can't verify user status, allow the request to continue
                // This prevents the filter from breaking the application
                System.out.println("Warning: Could not verify user status: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean checkUserStatus(String email) {
        // Check if user is admin
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            return adminOpt.get().isActive();
        }
        
        // Check if user is retail store
        Optional<RetailStore> storeOpt = retailStoreRepository.findByStoreEmail(email);
        if (storeOpt.isPresent()) {
            return storeOpt.get().isActive();
        }
        
        // If user not found, consider them inactive
        return false;
    }
}
