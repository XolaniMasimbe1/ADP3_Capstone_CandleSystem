package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    private String driverId;
    
    @Column(unique = true)
    private String username;
    
    private String passwordHash;
    private String email;
    private String phoneNumber;
    
    private String numberPlate;    // Required for drivers
    private String vehicleType;    // Required for drivers

    // A public no-argument constructor is needed for Jackson deserialization
    public Driver() {}

    protected Driver(Builder builder) {
        this.driverId = builder.driverId;
        this.username = builder.username;
        this.passwordHash = builder.passwordHash;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.numberPlate = builder.numberPlate;
        this.vehicleType = builder.vehicleType;
    }

    // Getters
    public String getDriverId() { return driverId; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getNumberPlate() { return numberPlate; }
    public String getVehicleType() { return vehicleType; }

    // Public setters are required by Jackson for deserialization
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public void setUsername(String username) { this.username = username; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setNumberPlate(String numberPlate) { this.numberPlate = numberPlate; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(driverId, driver.driverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }

    public static class Builder {
        private String driverId;
        private String username;
        private String passwordHash;
        private String email;
        private String phoneNumber;
        private String numberPlate;
        private String vehicleType;

        public Builder setDriverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setNumberPlate(String numberPlate) {
            this.numberPlate = numberPlate;
            return this;
        }

        public Builder setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder copy(Driver driver) {
            this.driverId = driver.driverId;
            this.username = driver.username;
            this.passwordHash = driver.passwordHash;
            this.email = driver.email;
            this.phoneNumber = driver.phoneNumber;
            this.numberPlate = driver.numberPlate;
            this.vehicleType = driver.vehicleType;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
}
