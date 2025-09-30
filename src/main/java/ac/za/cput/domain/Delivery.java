package ac.za.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Delivery {
    @Id
    private String deliveryNumber;
    private String status;


    protected Delivery() {}

    private Delivery(Builder builder) {
        this.deliveryNumber = builder.deliveryNumber;
        this.status = builder.status;

    }

    // Getters
    public String getDeliveryNumber() { return deliveryNumber; }
    public String getStatus() { return status; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Delivery delivery = (Delivery) o;
        return Objects.equals(deliveryNumber, delivery.deliveryNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryNumber);
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryNumber='" + deliveryNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public static class Builder {
        private String deliveryNumber;
        private String status;


        public Builder setDeliveryNumber(String deliveryNumber) {
            this.deliveryNumber = deliveryNumber;
            return this;
        }
        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }



        public Builder copy(Delivery delivery) {
            this.deliveryNumber = delivery.deliveryNumber;
            this.status = delivery.status;
            return this;
        }

        public Delivery build() {
            return new Delivery(this);
        }
    }
}