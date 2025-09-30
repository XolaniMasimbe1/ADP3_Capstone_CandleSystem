package ac.za.cput.repository;

import ac.za.cput.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    Optional<Driver> findByUsername(String username);
    Optional<Driver> findByEmail(String email);
    Optional<Driver> findByNumberPlate(String numberPlate);
}
