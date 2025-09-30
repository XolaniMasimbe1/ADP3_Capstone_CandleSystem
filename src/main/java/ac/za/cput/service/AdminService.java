package ac.za.cput.service;

import ac.za.cput.domain.Admin;
import ac.za.cput.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IService<Admin, String> {
    private final AdminRepository repository;

    @Autowired
    public AdminService(AdminRepository repository) {
        this.repository = repository;
    }

    @Override
    public Admin create(Admin admin) {
        return repository.save(admin);
    }

    @Override
    public Admin read(String adminId) {
        return repository.findById(adminId).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        if (repository.existsById(admin.getAdminId())) {
            return repository.save(admin);
        }
        return null;
    }

    public boolean delete(String adminId) {
        if (repository.existsById(adminId)) {
            repository.deleteById(adminId);
            return true;
        }
        return false;
    }

    public List<Admin> getAll() {
        return repository.findAll();
    }

    public Optional<Admin> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<Admin> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
