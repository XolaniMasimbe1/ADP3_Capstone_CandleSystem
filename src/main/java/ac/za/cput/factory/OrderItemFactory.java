package ac.za.cput.factory;

import ac.za.cput.domain.Candle;
import ac.za.cput.domain.Order;
import ac.za.cput.domain.OrderItem;
import ac.za.cput.util.Helper;

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/
public class OrderItemFactory {
    public static OrderItem createOrderItem(int quantity, double unitPrice,
                                            String category, Candle candle, Order order) {

        if (quantity <= 0 || unitPrice <= 0 || Helper.isNullOrEmpty(category) ||
                candle == null || order == null) {
            return null;
        }

        double subtotal = quantity * unitPrice;

        OrderItem orderItem = new OrderItem.Builder()
                .setQuantity(quantity)
                .setUnitPrice(unitPrice)
                .setSubtotal(subtotal)
                .setCategory(category)
                .build();

        // Set relationships after building the basic object
        //orderItem.setCandle(candle);
        //orderItem.setOrder(order);

        return orderItem;
    }
}