package ac.za.cput.repository;

import ac.za.cput.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    Optional<Driver> findByLicenseNumber(String licenseNumber);
    Optional<Driver> findByUser_Username(String username);
    Optional<Driver> findByUser_ContactDetails_Email(String email);
    Optional<Driver> findByUser_UserId(String userId);
}
