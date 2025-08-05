package ac.za.cput.factory;

import ac.za.cput.domain.Candle;
import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;
import ac.za.cput.util.Helper;

public class OrderItemFactory {
    public static OrderItem createOrderItem(int quantity, double unitPrice,
                                         String category, Candle candle, Order order) {
        // Input validation
        if (quantity <= 0 || unitPrice <= 0 || Helper.isNullOrEmpty(category) ||
                candle == null || order == null) {
            return null;
        }
        
        // Validate if unit price matches candle price
        if (unitPrice != candle.getPrice()) {
            return null;
        }

        double subtotal = quantity * unitPrice;
        
        return new OrderItem.Builder()
                .setId(Long.parseLong(Helper.generateNumericId(10)))
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .setSubtotal(subtotal)
                .setCategory(category)
                .build();
    }
}