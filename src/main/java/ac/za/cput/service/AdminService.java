package ac.za.cput.service;

import ac.za.cput.domain.Admin;
import ac.za.cput.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final AdminRepository repository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Admin create(Admin admin) {
        if (admin.getPasswordHash() != null && !admin.getPasswordHash().startsWith("$2a$")) {
            admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));
        }
        return repository.save(admin);
    }

    public Admin read(String adminId) {
        return this.repository.findById(adminId).orElse(null);
    }

    @Transactional
    public Admin update(Admin admin) {
        Admin existingAdmin = repository.findById(admin.getAdminId()).orElse(null);
        if (existingAdmin != null) {
            if (admin.getPasswordHash() == null || admin.getPasswordHash().equals("")) {
                admin.setPasswordHash(existingAdmin.getPasswordHash());
            } else if (!admin.getPasswordHash().startsWith("$2a$")) {
                admin.setPasswordHash(passwordEncoder.encode(admin.getPasswordHash()));
            }
        }
        return this.repository.save(admin);
    }

    @Transactional
    public boolean delete(String adminId) {
        if (this.repository.existsById(adminId)) {
            this.repository.deleteById(adminId);
            return true;
        }
        return false;
    }

    public List<Admin> getAll() {
        return this.repository.findAll();
    }

    public Optional<Admin> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public Optional<Admin> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }
}
