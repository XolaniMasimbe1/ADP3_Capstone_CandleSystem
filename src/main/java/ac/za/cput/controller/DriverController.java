package ac.za.cput.controller;

import ac.za.cput.domain.Driver;
import ac.za.cput.factory.DriverFactory;
import ac.za.cput.service.DriverService;
import ac.za.cput.service.UserService;
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
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public DriverController(DriverService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Driver> create(@RequestBody Driver driver) {
        Driver createdDriver = service.create(driver);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDriver);
    }

    @GetMapping("/read/{driverId}")
    public ResponseEntity<Driver> read(@PathVariable String driverId) {
        Driver driver = service.read(driverId);
        if (driver != null) {
            return ResponseEntity.ok(driver);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Driver> update(@RequestBody Driver driver) {
        Driver updatedDriver = service.update(driver);
        if (updatedDriver != null) {
            return ResponseEntity.ok(updatedDriver);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{driverId}")
    public ResponseEntity<Void> delete(@PathVariable String driverId) {
        boolean deleted = service.delete(driverId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find/{licenseNumber}")
    public ResponseEntity<Driver> findByLicenseNumber(@PathVariable String licenseNumber) {
        Optional<Driver> driver = service.findByLicenseNumber(licenseNumber);
        return driver.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Driver>> getAll() {
        List<Driver> allDrivers = service.getAll();
        return ResponseEntity.ok(allDrivers);
    }

    @PostMapping("/register")
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {
        try {
            // Check if username exists
            if (driver.getUser() != null && driver.getUser().getUsername() != null) {
                if (userService.findByUsername(driver.getUser().getUsername()).isPresent() || 
                    service.findByUsername(driver.getUser().getUsername()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Check if email exists
                if (driver.getUser().getContactDetails() != null && 
                    service.findByEmail(driver.getUser().getContactDetails().getEmail()).isPresent()) {
                    return ResponseEntity.badRequest().build();
                }

                // Create driver using DriverFactory
                Driver newDriver = DriverFactory.createDriver(
                        driver.getLicenseNumber(),
                        driver.getVehicleType(),
                        driver.getUser().getUsername(),
                        "defaultPassword", // You can set a default or get from request
                        driver.getUser().getContactDetails().getEmail(),
                        driver.getUser().getContactDetails().getPhoneNumber(),
                        driver.getUser().getContactDetails().getPostalCode(),
                        driver.getUser().getContactDetails().getStreet(),
                        driver.getUser().getContactDetails().getCity(),
                        driver.getUser().getContactDetails().getProvince(),
                        driver.getUser().getContactDetails().getCountry()
                );
                
                Driver savedDriver = service.create(newDriver);
                return ResponseEntity.ok(savedDriver);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Driver> login(@RequestBody Driver driver) {
        // Check if it's a driver
        if (driver.getUser() != null && driver.getUser().getUsername() != null) {
            Optional<Driver> optionalDriver = service.findByUsername(driver.getUser().getUsername());
            if (optionalDriver.isPresent()) {
                Driver foundDriver = optionalDriver.get();
                if (passwordEncoder.matches("defaultPassword", foundDriver.getUser().getPasswordHash())) {
                    return ResponseEntity.ok(foundDriver);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
