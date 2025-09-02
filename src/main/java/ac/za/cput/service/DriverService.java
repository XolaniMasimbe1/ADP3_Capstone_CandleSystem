package ac.za.cput.service;

import ac.za.cput.domain.Driver;
import ac.za.cput.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService {
    private final DriverRepository repository;

    @Autowired
    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Driver create(Driver driver) {
        return repository.save(driver);
    }

    public Driver read(String driverId) {
        return this.repository.findById(driverId).orElse(null);
    }

    public Driver update(Driver driver) {
        return this.repository.save(driver);
    }

    public boolean delete(String driverId) {
        if (this.repository.existsById(driverId)) {
            this.repository.deleteById(driverId);
            return true;
        }
        return false;
    }

    public List<Driver> getAll() {
        return this.repository.findAll();
    }

    public Optional<Driver> findByLicenseNumber(String licenseNumber) {
        return this.repository.findByLicenseNumber(licenseNumber);
    }

    public Optional<Driver> findByUsername(String username) {
        return this.repository.findByUser_Username(username);
    }

    public Optional<Driver> findByEmail(String email) {
        return this.repository.findByUser_ContactDetails_Email(email);
    }

    public Driver readByUserId(String userId) {
        return this.repository.findByUser_UserId(userId).orElse(null);
    }
}
