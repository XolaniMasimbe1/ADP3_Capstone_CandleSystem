package ac.za.cput.service;

import ac.za.cput.domain.ContactDetails;
import ac.za.cput.repository.ContactDetailsRepository;
import ac.za.cput.service.Imp.IContactDetailsService;
import ac.za.cput.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactDetailsService implements IContactDetailsService {
    private final ContactDetailsRepository repository;

    @Autowired
    public ContactDetailsService(ContactDetailsRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContactDetails create(ContactDetails contactDetails) {
        // Generate a unique ID before saving
        contactDetails.setContactId(Helper.generateId());
        return this.repository.save(contactDetails);
    }

    @Override
    public ContactDetails read(String contactId) {
        return this.repository.findById(contactId).orElse(null);
    }

    @Override
    public ContactDetails update(ContactDetails contactDetails) {
        if (this.repository.existsById(contactDetails.getContactId())) {
            return this.repository.save(contactDetails);
        }
        return null;
    }

    @Override
    public boolean delete(String contactId) {
        if (this.repository.existsById(contactId)) {
            this.repository.deleteById(contactId);
            return true;
        }
        return false;
    }

    @Override
    public List<ContactDetails> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<ContactDetails> findByContactId(String contactId) {
        return repository.findByContactId(contactId);
    }

    @Override
    public Optional<ContactDetails> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
