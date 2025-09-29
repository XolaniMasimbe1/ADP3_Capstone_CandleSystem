package ac.za.cput.controller;

import ac.za.cput.domain.ContactPerson;
import ac.za.cput.service.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/contact-person")
@Transactional
public class ContactPersonController {
    private final ContactPersonService service;

    @Autowired
    public ContactPersonController(ContactPersonService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ContactPerson create(@RequestBody ContactPerson contactPerson) {
        return service.create(contactPerson);
    }

    @GetMapping("/read/{contactPersonId}")
    public ContactPerson read(@PathVariable String contactPersonId) {
        return service.read(contactPersonId);
    }

    @PutMapping("/update")
    public ContactPerson update(@RequestBody ContactPerson contactPerson) {
        return service.update(contactPerson);
    }

    @DeleteMapping("/delete/{contactPersonId}")
    public ResponseEntity<String> delete(@PathVariable String contactPersonId) {
        boolean deleted = service.delete(contactPersonId);
        if (deleted) {
            return ResponseEntity.ok("Contact person deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public List<ContactPerson> getAll() {
        return service.getAll();
    }

    @GetMapping("/find/email/{emailAddress}")
    public Optional<ContactPerson> findByEmailAddress(@PathVariable String emailAddress) {
        return service.findByEmailAddress(emailAddress);
    }

    @GetMapping("/find/name")
    public List<ContactPerson> findByFirstNameAndLastName(@RequestParam String firstName, 
                                                         @RequestParam String lastName) {
        return service.findByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("/find/phone/{cellPhoneNumber}")
    public List<ContactPerson> findByCellPhoneNumber(@PathVariable String cellPhoneNumber) {
        return service.findByCellPhoneNumber(cellPhoneNumber);
    }
}
