package ac.za.cput.controller;

import ac.za.cput.domain.Address;
import ac.za.cput.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/address")
@Transactional
public class AddressController {
    private final AddressService service;

    @Autowired
    public AddressController(AddressService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Address create(@RequestBody Address address) {
        return service.create(address);
    }

    @GetMapping("/read/{addressId}")
    public Address read(@PathVariable String addressId) {
        return service.read(addressId);
    }

    @PutMapping("/update")
    public Address update(@RequestBody Address address) {
        return service.update(address);
    }

    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<String> delete(@PathVariable String addressId) {
        boolean deleted = service.delete(addressId);
        if (deleted) {
            return ResponseEntity.ok("Address deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public List<Address> getAll() {
        return service.getAll();
    }

    @GetMapping("/find/city/{city}")
    public List<Address> findByCity(@PathVariable String city) {
        return service.findByCity(city);
    }

    @GetMapping("/find/province/{province}")
    public List<Address> findByProvince(@PathVariable String province) {
        return service.findByProvince(province);
    }

    @GetMapping("/find/country/{country}")
    public List<Address> findByCountry(@PathVariable String country) {
        return service.findByCountry(country);
    }

    @GetMapping("/find/street")
    public Optional<Address> findByStreetDetails(@RequestParam String streetNumber, 
                                                @RequestParam String streetName, 
                                                @RequestParam String suburb, 
                                                @RequestParam String city) {
        return service.findByStreetNumberAndStreetNameAndSuburbAndCity(streetNumber, streetName, suburb, city);
    }
}
