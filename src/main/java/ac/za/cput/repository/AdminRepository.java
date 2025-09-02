package ac.za.cput.repository;

import ac.za.cput.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUser_Username(String username);
    Optional<Admin> findByUser_ContactDetails_Email(String email);
    Optional<Admin> findByUser_UserId(String userId);
}
