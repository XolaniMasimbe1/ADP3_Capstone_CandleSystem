package ac.za.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
public class Invoice {
    @Id
    private String invoiceNumber;
    private double totalAmount;

    protected Invoice() {}

    private Invoice(Builder builder) {
        this.invoiceNumber = builder.invoiceNumber;
        this.totalAmount = builder.totalAmount;
    }

    // Getters
    public String getInvoiceNumber() { return invoiceNumber; }
    public double getTotalAmount() { return totalAmount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(invoiceNumber, invoice.invoiceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNumber);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public static class Builder {
        private String invoiceNumber;
        private double totalAmount;

        public Builder setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }


        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder copy(Invoice invoice) {
            this.invoiceNumber = invoice.invoiceNumber;
            this.totalAmount = invoice.totalAmount;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
