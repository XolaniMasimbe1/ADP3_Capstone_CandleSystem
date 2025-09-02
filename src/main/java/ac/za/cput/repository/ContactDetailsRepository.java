package ac.za.cput.repository;

import ac.za.cput.domain.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactDetailsRepository extends JpaRepository<ContactDetails, String> {
    Optional<ContactDetails> findByContactId(String contactId);
    Optional<ContactDetails> findByEmail(String email);
}
