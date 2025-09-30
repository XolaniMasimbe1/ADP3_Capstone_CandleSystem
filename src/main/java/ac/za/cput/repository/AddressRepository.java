package ac.za.cput.repository;

import ac.za.cput.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByCity(String city);
    List<Address> findByProvince(String province);
    List<Address> findByCountry(String country);
    Optional<Address> findByStreetNumberAndStreetNameAndSuburbAndCity(String streetNumber, String streetName, String suburb, String city);
}
