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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private UserStatusFilter userStatusFilter;

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
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                
                // JWT Authentication endpoints (public)
                .requestMatchers("/auth/**").permitAll()
                
                // Forgot password endpoints (public)
                .requestMatchers("/forgot-password/**").permitAll()
                
                // Registration endpoints (public)
                .requestMatchers("/store/register", "/admin/register", "/driver/register", "/admin/create").permitAll()
                
                // Login endpoints (public - for mobile app authentication)
                .requestMatchers("/store/login", "/admin/login", "/driver/login").permitAll()
                
                // Product endpoints (public - everyone can view products)
                .requestMatchers("/product/**").permitAll()
                
                // Manufacture endpoints (public - for admin dashboard)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/manufacture/**").permitAll()
                .requestMatchers("/manufacture/**").authenticated()
                
                // Store endpoints (public - for admin dashboard)
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/store/provinces").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/store/all").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/store/read/**").permitAll()
                .requestMatchers("/store/**").authenticated()
                
                // Delivery endpoints (simple mobile access)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/delivery/create").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/delivery/read/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/delivery/all").permitAll()
                .requestMatchers("/delivery/**").authenticated()
                
                // Invoice endpoints (simple mobile access)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/invoice/create").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/invoice/read/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/invoice/all").permitAll()
                .requestMatchers("/invoice/**").authenticated()
                
                // Order endpoints (simple mobile + admin dashboard)
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/order/create").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.POST, "/order/create-with-email").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/order/read/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/order/store/**").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.GET, "/order/all").permitAll()
                .requestMatchers(org.springframework.http.HttpMethod.PUT, "/order/update").permitAll()
                .requestMatchers("/order/**").authenticated()
                
                // Test endpoints (public)
                .requestMatchers("/test/**").permitAll()
                
                
                // Admin only endpoints (require JWT authentication)
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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API endpoints
                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, e) -> res.sendError(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED))
                    .accessDeniedHandler((req, res, e) -> res.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN))
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
                .addFilterAfter(userStatusFilter, JwtAuthenticationFilter.class); // Add user status filter after JWT

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://127.0.0.1:3000",
                "http://192.168.14.13:3000",
                "http://localhost:19006",
                "http://localhost:19000"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
