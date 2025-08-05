package ac.za.cput.factory;

import ac.za.cput.domain.Candle;
import ac.za.cput.util.Helper;

/*

 * Author: Basetsana Masisi
 * Student Number: 222309385
 * Date: 23/07/2025
 **/
public class CandleFactory {
    public static Candle createCandle(String name, String scent, String color,
                                      String size, double price, int stockQuantity) {

        if (Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(scent) ||
                Helper.isNullOrEmpty(color) || Helper.isNullOrEmpty(size) ||
                price <= 0 || stockQuantity < 0) {
            return null;
        }

        String candleNumber = "CAND-" + Helper.generateId();

        return new Candle.Builder()
                .setCandleNumber(candleNumber)
                .setName(name)
                .setScent(scent)
                .setColor(color)
                .setSize(size)
                .setPrice(price)
                .setStockQuantity(stockQuantity)
                .build();
    }
}