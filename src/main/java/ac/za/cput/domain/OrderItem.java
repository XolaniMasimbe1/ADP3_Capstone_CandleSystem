package ac.za.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class OrderItem {
    @Id
    private Long id;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    private String category;


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "candle_number")

    private Candle candle;

    protected OrderItem() {
    }

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.subtotal = builder.subtotal;
        this.category = builder.category;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                ", category='" + category + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private int quantity;
        private double unitPrice;
        private double subtotal;
        private String category;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder setSubtotal(double subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder copy(OrderItem orderItem) {
            this.id = orderItem.id;
            this.quantity = orderItem.quantity;
            this.unitPrice = orderItem.unitPrice;
            this.subtotal = orderItem.subtotal;
            this.category = orderItem.category;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
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
