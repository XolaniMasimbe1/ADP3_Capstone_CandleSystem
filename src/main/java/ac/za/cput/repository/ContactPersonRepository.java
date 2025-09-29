package ac.za.cput.repository;

import ac.za.cput.domain.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, String> {
    Optional<ContactPerson> findByEmailAddress(String emailAddress);
    List<ContactPerson> findByFirstNameAndLastName(String firstName, String lastName);
    List<ContactPerson> findByCellPhoneNumber(String cellPhoneNumber);
}
