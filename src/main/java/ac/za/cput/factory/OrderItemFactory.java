package ac.za.cput.factory;

import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;
import ac.za.cput.domain.Product;

public class OrderItemFactory {
    public static OrderItem createOrderItem(int quantity, Product product, Order order) {
        if (quantity <= 0 || product == null || order == null) {
            return null;
        }

        return new OrderItem.Builder()
                .setQuantity(quantity)
                .setUnitPrice(product.getPrice())
                .setProduct(product)
                .setOrder(order)
                .build();
    }

    // Overloaded method for testing when order isn't available yet
    public static OrderItem createOrderItem(int quantity, Product product) {
        if (quantity <= 0 || product == null) {
            return null;
        }

        return new OrderItem.Builder()
                .setQuantity(quantity)
                .setUnitPrice(product.getPrice())
                .setProduct(product)
                .build();
    }
}