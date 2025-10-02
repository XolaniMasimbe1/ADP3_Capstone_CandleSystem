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
                
                
                // Admin only endpoints
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Driver only endpoints  
                .requestMatchers("/driver/**").hasRole("DRIVER")
                
                // Retail store only endpoints
                .requestMatchers("/retail/**").hasRole("RETAIL_STORE")
                
                // Any authenticated user
                .requestMatchers("/api/**").authenticated()
                
                // All other requests need authentication
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
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
