package ac.za.cput.controller;

import ac.za.cput.domain.Driver;
import ac.za.cput.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/driver")
@Transactional
public class DriverController {
    private final DriverService service;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public DriverController(DriverService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Driver create(@RequestBody Driver driver) {
        return service.create(driver);
    }

    @GetMapping("/read/{driverId}")
    public Driver read(@PathVariable String driverId) {
        return service.read(driverId);
    }

    @PutMapping("/update")
    public Driver update(@RequestBody Driver driver) {
        return service.update(driver);
    }

    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<String> delete(@PathVariable String driverId) {
        boolean deleted = service.delete(driverId);
        if (deleted) {
            return ResponseEntity.ok("Driver deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/username/{username}")
    public Optional<Driver> findByUsername(@PathVariable String username) {
        return service.findByUsername(username);
    }

    @GetMapping("/find/email/{email}")
    public Optional<Driver> findByEmail(@PathVariable String email) {
        return service.findByEmail(email);
    }

    @GetMapping("/find/license/{licenseNumber}")
    public Optional<Driver> findByLicenseNumber(@PathVariable String licenseNumber) {
        return service.findByLicenseNumber(licenseNumber);
    }

    @GetMapping("/all")
    public List<Driver> getAll() {
        return service.getAll();
    }

    @PostMapping("/register")
    public ResponseEntity<Driver> register(@RequestBody Driver driver) {
        try {
            // Check if driver username exists
            if (service.findByUsername(driver.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            // Check if driver email exists
            if (service.findByEmail(driver.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            // Check if license number exists
            if (service.findByLicenseNumber(driver.getLicenseNumber()).isPresent()) {
                return ResponseEntity.badRequest().build();
            }

            // Hash the password
            driver.setPasswordHash(passwordEncoder.encode(driver.getPasswordHash()));

            Driver savedDriver = service.create(driver);
            return ResponseEntity.ok(savedDriver);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Driver> login(@RequestBody Driver loginRequest) {
        try {
            // Find driver by username
            Optional<Driver> optionalDriver = service.findByUsername(loginRequest.getUsername());
            if (optionalDriver.isPresent()) {
                Driver foundDriver = optionalDriver.get();
                // Verify password
                if (passwordEncoder.matches(loginRequest.getPasswordHash(), foundDriver.getPasswordHash())) {
                    return ResponseEntity.ok(foundDriver);
                }
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
