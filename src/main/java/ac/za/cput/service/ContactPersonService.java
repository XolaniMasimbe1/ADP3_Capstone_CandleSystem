package ac.za.cput.service;

import ac.za.cput.domain.ContactPerson;
import ac.za.cput.repository.ContactPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactPersonService implements IService<ContactPerson, String> {
    private final ContactPersonRepository repository;

    @Autowired
    public ContactPersonService(ContactPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContactPerson create(ContactPerson contactPerson) {
        return repository.save(contactPerson);
    }

    @Override
    public ContactPerson read(String contactPersonId) {
        return repository.findById(contactPersonId).orElse(null);
    }

    @Override
    public ContactPerson update(ContactPerson contactPerson) {
        if (repository.existsById(contactPerson.getContactPersonId())) {
            return repository.save(contactPerson);
        }
        return null;
    }

    public boolean delete(String contactPersonId) {
        if (repository.existsById(contactPersonId)) {
            repository.deleteById(contactPersonId);
            return true;
        }
        return false;
    }

    public List<ContactPerson> getAll() {
        return repository.findAll();
    }

    public Optional<ContactPerson> findByEmailAddress(String emailAddress) {
        return repository.findByEmailAddress(emailAddress);
    }

    public List<ContactPerson> findByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<ContactPerson> findByCellPhoneNumber(String cellPhoneNumber) {
        return repository.findByCellPhoneNumber(cellPhoneNumber);
    }
}
