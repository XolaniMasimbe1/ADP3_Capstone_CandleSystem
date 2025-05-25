package ac.za.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
/*
    * Invoice.java
    * Pojo for Invoice
    * Author: Xolani Masimbe
    * Student Number: 222410817
    * Date: 25 May 2025
 **/

import java.time.LocalDate;
@Entity
public class Invoice {
    @Id
    private String invoiceNumber;
    private String invoiceDate;
    private String invoiceAmount;


    protected Invoice(){

    }
    private Invoice(Builder builder) {
        this.invoiceNumber = builder.invoiceNumber;
        this.invoiceDate = builder.invoiceDate;
        this.invoiceAmount = builder.invoiceAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getInvoiceAmount() {
        return invoiceAmount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", invoiceAmount='" + invoiceAmount + '\'' +
                '}';
    }

    public static class Builder {
        private String invoiceNumber;
        private String invoiceDate;
        private String invoiceAmount;

        public Builder setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
            return this;
        }

        public Builder setInvoiceDate(String invoiceDate) {
            this.invoiceDate = invoiceDate;
            return this;
        }

        public Builder setInvoiceAmount(String invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
            return this;
        }
        public Builder copy(Invoice invoice){
            this.invoiceNumber = invoice.invoiceNumber;
            this.invoiceDate = invoice.invoiceDate;
            this.invoiceAmount = invoice.invoiceAmount;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
