package ac.za.cput.domain;

import ac.za.cput.domain.Enum.PaymentType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class PaymentMethod {
    @Id
    private String paymentMethodId;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private LocalDateTime paymentDate;

    protected PaymentMethod() {}

    private PaymentMethod(Builder builder) {
        this.paymentMethodId = builder.paymentMethodId;
        this.type = builder.type;
        this.paymentDate = builder.paymentDate;
    }

    // Getters
    public String getPaymentMethodId() { return paymentMethodId; }
    public PaymentType getType() { return type; }
    public LocalDateTime getPaymentDate() { return paymentDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return Objects.equals(paymentMethodId, that.paymentMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethodId);
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentMethodId='" + paymentMethodId + '\'' +
                ", type=" + type +
                ", paymentDate=" + paymentDate +
                '}';
    }

    public static class Builder {
        private String paymentMethodId;
        private PaymentType type;
        private LocalDateTime paymentDate;

        public Builder setPaymentMethodId(String paymentMethodId) {
            this.paymentMethodId = paymentMethodId;
            return this;
        }

        public Builder setType(PaymentType type) {
            this.type = type;
            return this;
        }


        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder copy(PaymentMethod paymentMethod) {
            this.paymentMethodId = paymentMethod.paymentMethodId;
            this.type = paymentMethod.type;
            this.paymentDate = paymentMethod.paymentDate;
            return this;
        }

        public PaymentMethod build() {
            return new PaymentMethod(this);
        }
    }
}