package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productNumber")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_number", referencedColumnName = "orderNumber")
    @JsonBackReference
    private Order order;

    protected OrderItem() {}

    private OrderItem(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.unitPrice = builder.unitPrice;
        this.product = builder.product;
        this.order = builder.order;
    }

    // Getters
    public Long getId() { return id; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public Product getProduct() { return product; }
    public Order getOrder() { return order; }

    // Setters
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", product=" + (product != null ? product.getName() : "null") +
                ", order=" + (order != null ? order.getOrderNumber() : "null") +
                '}';
    }

    public static class Builder {
        private Long id;
        private int quantity;
        private double unitPrice;
        private Product product;
        private Order order;

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

        public Builder setProduct(Product product) {
            this.product = product;
            return this;
        }

        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder copy(OrderItem orderItem) {
            this.id = orderItem.id;
            this.quantity = orderItem.quantity;
            this.unitPrice = orderItem.unitPrice;
            this.product = orderItem.product;
            this.order = orderItem.order;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}