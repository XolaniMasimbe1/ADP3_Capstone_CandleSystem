package ac.za.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;



@Entity
public class Order {
    @Id
    private int orderNumber;
    private LocalDate orderDate;
    private String status;
    private String totalAmount;

    @OneToOne
    private Invoice invoice;

    protected Order() {
    }

    private Order(Builder builder) {
        this.orderNumber = builder.orderNumber;
        this.orderDate = builder.orderDate;
        this.status = builder.status;
        this.totalAmount = builder.totalAmount;
        this.invoice = builder.invoice;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", invoice=" + invoice +
                '}';
    }

    public static class Builder {
        private int orderNumber;
        private LocalDate orderDate;
        private String status;
        private String totalAmount;
        private Invoice invoice;

        public Builder setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder setInvoice(Invoice invoice) {
            this.invoice = invoice;
            return this;
        }

        public Builder copy(Order order) {
            this.orderNumber = order.orderNumber;
            this.orderDate = order.orderDate;
            this.status = order.status;
            this.totalAmount = order.totalAmount;
            this.invoice = order.invoice;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}


/*
 * Order.java
 * Pojo for Order
 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/