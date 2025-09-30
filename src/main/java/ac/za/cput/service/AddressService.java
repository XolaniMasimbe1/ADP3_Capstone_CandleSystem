package ac.za.cput.service;

import ac.za.cput.domain.Address;
import ac.za.cput.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IService<Address, String> {
    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Address create(Address address) {
        return repository.save(address);
    }

    @Override
    public Address read(String addressId) {
        return repository.findById(addressId).orElse(null);
    }

    @Override
    public Address update(Address address) {
        if (repository.existsById(address.getAddressId())) {
            return repository.save(address);
        }
        return null;
    }

    public boolean delete(String addressId) {
        if (repository.existsById(addressId)) {
            repository.deleteById(addressId);
            return true;
        }
        return false;
    }

    public List<Address> getAll() {
        return repository.findAll();
    }

    public List<Address> findByCity(String city) {
        return repository.findByCity(city);
    }

    public List<Address> findByProvince(String province) {
        return repository.findByProvince(province);
    }

    public List<Address> findByCountry(String country) {
        return repository.findByCountry(country);
    }

    public Optional<Address> findByStreetNumberAndStreetNameAndSuburbAndCity(String streetNumber, String streetName, String suburb, String city) {
        return repository.findByStreetNumberAndStreetNameAndSuburbAndCity(streetNumber, streetName, suburb, city);
    }
}
