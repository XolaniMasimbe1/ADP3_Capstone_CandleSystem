package ac.za.cput.service;

import ac.za.cput.domain.Driver;
import ac.za.cput.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverService implements IService<Driver, String> {
    private final DriverRepository repository;

    @Autowired
    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    @Override
    public Driver create(Driver driver) {
        return repository.save(driver);
    }

    @Override
    public Driver read(String driverId) {
        return repository.findById(driverId).orElse(null);
    }

    @Override
    public Driver update(Driver driver) {
        if (repository.existsById(driver.getDriverId())) {
            return repository.save(driver);
        }
        return null;
    }

    public boolean delete(String driverId) {
        if (repository.existsById(driverId)) {
            repository.deleteById(driverId);
            return true;
        }
        return false;
    }

    public List<Driver> getAll() {
        return repository.findAll();
    }

    public Optional<Driver> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public Optional<Driver> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Driver> findByLicenseNumber(String licenseNumber) {
        return repository.findByLicenseNumber(licenseNumber);
    }
}
