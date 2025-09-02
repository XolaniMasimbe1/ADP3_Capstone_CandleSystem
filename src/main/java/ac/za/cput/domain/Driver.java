package ac.za.cput.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    private String driverId;
    
    @Column(name = "license_number", unique = true)
    private String licenseNumber;
    
    @Column(name = "vehicle_type")
    private String vehicleType;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    // A public no-argument constructor is needed for Jackson deserialization
    public Driver() {}

    protected Driver(Builder builder) {
        this.driverId = builder.driverId;
        this.licenseNumber = builder.licenseNumber;
        this.vehicleType = builder.vehicleType;
        this.user = builder.user;
    }

    // Getters
    public String getDriverId() { return driverId; }
    public String getLicenseNumber() { return licenseNumber; }
    public String getVehicleType() { return vehicleType; }
    public User getUser() { return user; }

    // Public setters are required by Jackson
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", user=" + user +
                '}';
    }

    public static class Builder {
        private String driverId;
        private String licenseNumber;
        private String vehicleType;
        private User user;

        public Builder setDriverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public Builder setLicenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return this;
        }

        public Builder setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder setUser(User user) {
            this.user = user;
            return this;
        }

        public Builder copy(Driver driver) {
            this.driverId = driver.driverId;
            this.licenseNumber = driver.licenseNumber;
            this.vehicleType = driver.vehicleType;
            this.user = driver.user;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
}
