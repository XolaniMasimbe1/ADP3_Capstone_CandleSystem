package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Manufacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manufacturerNumber;
    private String manufacturerName;

    protected Manufacture() {
    }

    private Manufacture(Builder builder) {
        this.manufacturerNumber = builder.manufacturerNumber;
        this.manufacturerName = builder.manufacturerName;
    }

    // Getters
    public Long getManufacturerNumber() { return manufacturerNumber; }
    public String getManufacturerName() { return manufacturerName; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manufacture that = (Manufacture) o;
        return Objects.equals(manufacturerNumber, that.manufacturerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturerNumber);
    }

    @Override
    public String toString() {
        return "Manufacture{" +
                "manufacturerNumber=" + manufacturerNumber +
                ", manufacturerName='" + manufacturerName + '\'' +
                '}';
    }

    public static class Builder {
        private Long manufacturerNumber;
        private String manufacturerName;

        public Builder setManufacturerNumber(Long manufacturerNumber) {
            this.manufacturerNumber = manufacturerNumber;
            return this;
        }

        public Builder setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
            return this;
        }



        public Builder copy(Manufacture manufacture) {
            this.manufacturerNumber = manufacture.manufacturerNumber;
            this.manufacturerName = manufacture.manufacturerName;
            return this;
        }

        public Manufacture build() {
            return new Manufacture(this);
        }
    }
}