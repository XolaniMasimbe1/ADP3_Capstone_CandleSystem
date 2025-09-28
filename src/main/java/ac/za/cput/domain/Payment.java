package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;
import ac.za.cput.domain.Enum.BankName;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    private String paymentNumber;
    private double totalAmount;
    
    // South African Card Payment Details
    @Column(name = "card_number", length = 16)
    private String cardNumber;
    
    @Column(name = "name_on_card")
    private String nameOnCard;
    
    @Column(name = "expiry_date", length = 5)
    private String expiryDate; // Format: MM/YY
    
    @Column(name = "cvv", length = 3)
    private String cvv;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "bank_name")
    private BankName bank;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;


    public Payment() {}

    private Payment(Builder builder) {
        this.paymentNumber = builder.paymentNumber;
        this.totalAmount = builder.totalAmount;
        this.cardNumber = builder.cardNumber;
        this.nameOnCard = builder.nameOnCard;
        this.expiryDate = builder.expiryDate;
        this.cvv = builder.cvv;
        this.bank = builder.bank;
        this.paymentMethod = builder.paymentMethod;
    }

    // Getters
    public String getPaymentNumber() { return paymentNumber; }
    public double getTotalAmount() { return totalAmount; }
    public String getCardNumber() { return cardNumber; }
    public String getNameOnCard() { return nameOnCard; }
    public String getExpiryDate() { return expiryDate; }
    public String getCvv() { return cvv; }
    public BankName getBank() { return bank; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }


    // Setters
    public void setPaymentNumber(String paymentNumber) { this.paymentNumber = paymentNumber; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public void setNameOnCard(String nameOnCard) { this.nameOnCard = nameOnCard; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
    public void setCvv(String cvv) { this.cvv = cvv; }
    public void setBank(BankName bank) { this.bank = bank; }
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
                ", cardNumber='" + (cardNumber != null ? "****" + cardNumber.substring(cardNumber.length() - 4) : "null") + '\'' +
                ", nameOnCard='" + nameOnCard + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + (cvv != null ? "***" : "null") + '\'' +
                ", bank=" + bank +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    public static class Builder {
        private String paymentNumber;
        private double totalAmount;
        private String cardNumber;
        private String nameOnCard;
        private String expiryDate;
        private String cvv;
        private BankName bank;
        private PaymentMethod paymentMethod;

        public Builder setPaymentNumber(String paymentNumber) {
            this.paymentNumber = paymentNumber;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder setNameOnCard(String nameOnCard) {
            this.nameOnCard = nameOnCard;
            return this;
        }

        public Builder setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public Builder setCvv(String cvv) {
            this.cvv = cvv;
            return this;
        }

        public Builder setBank(BankName bank) {
            this.bank = bank;
            return this;
        }

        public Builder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentNumber = payment.paymentNumber;
            this.totalAmount = payment.totalAmount;
            this.cardNumber = payment.cardNumber;
            this.nameOnCard = payment.nameOnCard;
            this.expiryDate = payment.expiryDate;
            this.cvv = payment.cvv;
            this.bank = payment.bank;
            this.paymentMethod = payment.paymentMethod;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
