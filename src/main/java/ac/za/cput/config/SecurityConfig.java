package ac.za.cput.config;

import ac.za.cput.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(authz -> authz
                // Public endpoints
                .requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                
                // Registration endpoints (public)
                .requestMatchers("/store/register", "/admin/register", "/driver/register").permitAll()
                
                // Login endpoints (public - for mobile app authentication)
                .requestMatchers("/store/login", "/admin/login", "/driver/login").permitAll()
                
                // Admin creation and read endpoints (public for testing and initial setup)
                .requestMatchers("/admin/create", "/admin/read/**", "/admin/all", "/admin/update-password").permitAll()
                
                // Store management endpoints (public - for mobile app) - MUST come before other rules
                .requestMatchers("/store/update", "/store/update/**", "/store/read/**", "/store/find/**", "/store/all", "/store/create").permitAll()
                
                // Explicitly allow PATCH method for store updates
                .requestMatchers(HttpMethod.PATCH, "/store/update/**").permitAll()
                
                // Product endpoints (public - everyone can view products)
                .requestMatchers("/product/**").permitAll()
                
                // Delivery endpoints (public - for order creation)
                .requestMatchers("/delivery/**").permitAll()
                
                // Invoice endpoints (public - for order creation)
                .requestMatchers("/invoice/**").permitAll()
                
                // Order endpoints (public - for order creation)
                .requestMatchers("/order/**").permitAll()
                
                // Test endpoints (public)
                .requestMatchers("/test/**").permitAll()
                
                // Forgot password endpoints (public)
                .requestMatchers("/forgot-password/**").permitAll()
                
                // Admin management endpoints (require ADMIN role)
                .requestMatchers("/admin/update", "/admin/delete/**").hasRole("ADMIN")
                
                // Driver only endpoints  
                .requestMatchers("/driver/**").hasRole("DRIVER")
                
                // Retail store only endpoints (MUST come after /store/** rules)
                .requestMatchers("/retail/**").hasRole("RETAIL_STORE")
                
                // Any authenticated user
                .requestMatchers("/api/**").authenticated()
                
                // All other requests need authentication
                .anyRequest().authenticated()
            )
            // Removed formLogin since we're using API-based authentication
            // Authentication is handled through /admin/login, /driver/login, /store/login endpoints
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF for API endpoints

        return http.build();
    }
}
