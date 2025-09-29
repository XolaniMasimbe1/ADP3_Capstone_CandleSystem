package ac.za.cput.factory;

import ac.za.cput.domain.Address;
import ac.za.cput.domain.Enum.Province;
import ac.za.cput.util.Helper;

public class AddressFactory {
    
    public static Address createAddress(String streetNumber, String streetName, String suburb, 
                                      String city, String postalCode, Province province, String country) {
        if (Helper.isNullOrEmpty(streetNumber) || Helper.isNullOrEmpty(streetName) || 
            Helper.isNullOrEmpty(suburb) || Helper.isNullOrEmpty(city) || 
            Helper.isNullOrEmpty(postalCode) || province == null || Helper.isNullOrEmpty(country)) {
            return null;
        }

        String addressId = Helper.generateId();

        return new Address.Builder()
                .setAddressId(addressId)
                .setStreetNumber(streetNumber)
                .setStreetName(streetName)
                .setSuburb(suburb)
                .setCity(city)
                .setPostalCode(postalCode)
                .setProvince(province)
                .setCountry(country)
                .build();
    }
    
    public static Address createAddress(String streetNumber, String streetName, String suburb, 
                                      String city, String postalCode, String provinceString, String country) {
        Province province = null;
        try {
            province = Province.valueOf(provinceString.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            // If province string doesn't match enum, return null
            return null;
        }
        
        return createAddress(streetNumber, streetName, suburb, city, postalCode, province, country);
    }
}
