package ac.za.cput.config;

import ac.za.cput.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Simple JWT Authentication Filter
 * This filter checks for JWT tokens in requests and sets up authentication
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtService jwtService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        // Get token from Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        
        // Check if token exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Extract the token (remove "Bearer " prefix)
        jwt = authHeader.substring(7);
        
        try {
            // Extract email and role from token
            userEmail = jwtService.extractEmail(jwt);
            String role = jwtService.extractRole(jwt);
            String userId = jwtService.extractUserId(jwt);
            String name = jwtService.extractName(jwt);
            
            // If we have email and token is valid, set up authentication
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                // Validate token
                if (jwtService.validateToken(jwt, userEmail)) {
                    
                    // Create authentication token with role
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                    );
                    
                    // Set additional details
                    authToken.setDetails(userId + ":" + name);
                    
                    // Set authentication in security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // If token is invalid, clear security context
            SecurityContextHolder.clearContext();
        }
        
        filterChain.doFilter(request, response);
    }
}
