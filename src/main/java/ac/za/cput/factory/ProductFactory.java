package ac.za.cput.factory;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.domain.Product;
import ac.za.cput.util.Helper;

public class ProductFactory {
    public static Product createProduct(String name, double price, int stockQuantity, String scent, String color, String size, Manufacture manufacturer) {
        if (Helper.isNullOrEmpty(name) || price < 0 || stockQuantity < 0 || manufacturer == null) {
            return null;
        }

        String productNumber = Helper.generateId();
        return new Product.Builder()
                .setProductNumber(productNumber)
                .setName(name)
                .setPrice(price)
                .setStockQuantity(stockQuantity)
                .setScent(scent)
                .setColor(color)
                .setSize(size)
                .setManufacturer(manufacturer)
                .build();
    }
}