package ac.za.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Payment {
    @Id
    private String paymentNumber;
    private double totalAmount;


    @ManyToOne
    private PaymentMethod paymentMethod;


    public Payment() {}

    private Payment(Builder builder) {
        this.paymentNumber = builder.paymentNumber;
        this.totalAmount = builder.totalAmount;
        this.paymentMethod = builder.paymentMethod;
    }

    // Getters
    public String getPaymentNumber() { return paymentNumber; }
    public double getTotalAmount() { return totalAmount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }


    // Setters
    public void setPaymentNumber(String paymentNumber) { this.paymentNumber = paymentNumber; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(paymentNumber, payment.paymentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentNumber);
    }


    @Override
    public String toString() {
        return "Payment{" +
                "paymentNumber='" + paymentNumber + '\'' +
                ", totalAmount=" + totalAmount +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    public static class Builder {
        private String paymentNumber;
        private double totalAmount;
        private LocalDateTime paymentDate;
        private String status;
        private PaymentMethod paymentMethod;

        public Builder setPaymentNumber(String paymentNumber) {
            this.paymentNumber = paymentNumber;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentNumber = payment.paymentNumber;
            this.totalAmount = payment.totalAmount;
            this.paymentMethod = payment.paymentMethod;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
