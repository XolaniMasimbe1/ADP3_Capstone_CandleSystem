package ac.za.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    private String orderNumber;
    private LocalDate orderDate;
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private RetailStore retailStore;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<OrderItem> orderItems = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_number", referencedColumnName = "deliveryNumber")
    private Delivery delivery;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "invoice_number", referencedColumnName = "invoiceNumber")
    private Invoice invoice;



    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    protected Order() {}

    private Order(Builder builder) {
        this.orderNumber = builder.orderNumber;
        this.orderDate = builder.orderDate;
        this.orderStatus = builder.orderStatus;
        this.retailStore = builder.retailStore;
        this.delivery = builder.delivery;
        this.invoice = builder.invoice;


        if (builder.orderItems != null) {
            builder.orderItems.forEach(this::addOrderItem);
        }
    }
    public void reestablishOrderItemsRelationship() {
        if (this.orderItems != null) {
            this.orderItems.forEach(item -> item.setOrder(this));
        }
    }

    // Getters
    public String getOrderNumber() { return orderNumber; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getOrderStatus() { return orderStatus; }
    public RetailStore getRetailStore() { return retailStore; }
    public Set<OrderItem> getOrderItems() { return orderItems; }
    public Delivery getDelivery() { return delivery; }
    public Invoice getInvoice() { return invoice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderNumber, order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", orderDate=" + orderDate +
                ", orderStatus='" + orderStatus + '\'' +
                ", retailStore=" + (retailStore != null ? retailStore.getStoreNumber() : null) +
                ", orderItemsSize=" + (orderItems != null ? orderItems.size() : 0) +
                '}';
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public static class Builder {
        private String orderNumber;
        private LocalDate orderDate;
        private String orderStatus;
        private RetailStore retailStore;
        private Set<OrderItem> orderItems;
        private Delivery delivery;
        private Invoice invoice;

        public Builder setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder setOrderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }


        public Builder setRetailStore(RetailStore retailStore) {
            this.retailStore = retailStore;
            return this;
        }

        public Builder setOrderItems(Set<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        public Builder setDelivery(Delivery delivery) {
            this.delivery = delivery;
            return this;
        }

        public Builder setInvoice(Invoice invoice) {
            this.invoice = invoice;
            return this;
        }


        public Builder copy(Order order) {
            this.orderNumber = order.orderNumber;
            this.orderDate = order.orderDate;
            this.orderStatus = order.orderStatus;
            this.retailStore = order.retailStore;
            this.orderItems = order.orderItems;
            this.delivery = order.delivery;
            this.invoice = order.invoice;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}