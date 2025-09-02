package ac.za.cput.factory;

import ac.za.cput.domain.Driver;
import ac.za.cput.domain.User;
import ac.za.cput.domain.Enum.UserRole;
import ac.za.cput.util.Helper;

public class DriverFactory {

    public static Driver createDriver(String licenseNumber, String vehicleType, String username, String password, String email, String phoneNumber,
                                      String postalCode, String street, String city, String province, String country) {
        if (Helper.isNullOrEmpty(licenseNumber) || Helper.isNullOrEmpty(vehicleType) || 
            Helper.isNullOrEmpty(username) || Helper.isNullOrEmpty(password) || 
            Helper.isNullOrEmpty(email) || Helper.isNullOrEmpty(phoneNumber)) {
            return null;
        }
        
        String driverId = Helper.generateId();
        
        // Create User first with all contact details
        User user = UserFactory.createUser(username, password, UserRole.DRIVER, email, phoneNumber, 
                                          postalCode, street, city, province, country);
        
        // Create Driver with reference to User
        Driver.Builder builder = new Driver.Builder();
        return builder
                .setDriverId(driverId)
                .setLicenseNumber(licenseNumber)
                .setVehicleType(vehicleType)
                .setUser(user)
                .build();
    }
}
