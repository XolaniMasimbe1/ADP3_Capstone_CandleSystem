package ac.za.cput.factory;

import ac.za.cput.domain.*;
import ac.za.cput.util.Helper;

import java.time.LocalDate;
import java.util.Set;

public class OrderFactory {

    public static Order createOrder(
            String orderStatus,
            RetailStore retailStore,
            Set<OrderItem> orderItems,
            Delivery delivery,
            Invoice invoice) {

        if (Helper.isNullOrEmpty(orderStatus) || retailStore == null
                || orderItems == null || orderItems.isEmpty()
                || delivery == null || invoice == null) {
            return null;
        }

        String orderNumber = Helper.generateId();

        Order order = new Order.Builder()
                .setOrderNumber(orderNumber)
                .setOrderDate(LocalDate.now())
                .setOrderStatus(orderStatus)
                .setRetailStore(retailStore)
                .setOrderItems(orderItems)
                .setDelivery(delivery)
                .setInvoice(invoice)
                .build();

        // Set the order reference for each order item
        orderItems.forEach(item -> item.setOrder(order));

        return order;
    }

    public static Order createBasicOrder(String orderStatus, RetailStore retailStore, Set<OrderItem> orderItems) {
        if (Helper.isNullOrEmpty(orderStatus) || retailStore == null || orderItems == null || orderItems.isEmpty()) {
            return null;
        }
        return new Order.Builder()
                .setOrderNumber( Helper.generateId())
                .setOrderDate(LocalDate.now())
                .setOrderStatus(orderStatus)
                .setRetailStore(retailStore)
                .setOrderItems(orderItems)
                .build();
    }
}