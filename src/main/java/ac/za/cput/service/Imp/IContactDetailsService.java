package ac.za.cput.service.Imp;

import ac.za.cput.domain.ContactDetails;

import java.util.List;
import java.util.Optional;

public interface IContactDetailsService {
    ContactDetails create(ContactDetails contactDetails);
    ContactDetails read(String contactId);
    ContactDetails update(ContactDetails contactDetails);
    boolean delete(String contactId);
    List<ContactDetails> getAll();
    Optional<ContactDetails> findByContactId(String contactId);
    Optional<ContactDetails> findByEmail(String email);
}
