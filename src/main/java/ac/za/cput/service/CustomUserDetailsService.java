package ac.za.cput.service;

import ac.za.cput.domain.Admin;
import ac.za.cput.domain.Driver;
import ac.za.cput.domain.RetailStore;
import ac.za.cput.repository.AdminRepository;
import ac.za.cput.repository.DriverRepository;
import ac.za.cput.repository.RetailStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RetailStoreRepository retailStoreRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String password = null;

        // Try to find user in Admin table
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            password = admin.get().getPasswordHash();
        } else {
            // Try to find user in Driver table
            Optional<Driver> driver = driverRepository.findByUsername(username);
            if (driver.isPresent()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));
                password = driver.get().getPasswordHash();
            } else {
                // Try to find user in RetailStore table
                Optional<RetailStore> retailStore = retailStoreRepository.findByStoreEmail(username);
                if (retailStore.isPresent()) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_RETAIL_STORE"));
                    password = retailStore.get().getPasswordHash();
                } else {
                    throw new UsernameNotFoundException("User not found: " + username);
                }
            }
        }

        if (password == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new User(username, password, true, true, true, true, authorities);
    }
}
