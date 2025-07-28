package ac.za.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Candle {
    @Id
    private String candleNumber;
    private String name;
    private String scent;
    private String color;
    private String size;
    private double price;
    private int stockQuantity;

    protected Candle() {
    }

    private Candle(Builder builder) {
        this.candleNumber = builder.candleNumber;
        this.name = builder.name;
        this.scent = builder.scent;
        this.color = builder.color;
        this.size = builder.size;
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;
    }

    public String getCandleNumber() {
        return candleNumber;
    }

    public String getName() {
        return name;
    }

    public String getScent() {
        return scent;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public String toString() {
        return "Candle{" +
                "candleNumber='" + candleNumber + '\'' +
                ", name='" + name + '\'' +
                ", scent='" + scent + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    public static class Builder {
        private String candleNumber;
        private String name;
        private String scent;
        private String color;
        private String size;
        private double price;
        private int stockQuantity;

        public Builder setCandleNumber(String candleNumber) {
            this.candleNumber = candleNumber;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setScent(String scent) {
            this.scent = scent;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setSize(String size) {
            this.size = size;
            return this;
        }

        public Builder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder setStockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public Builder copy(Candle candle) {
            this.candleNumber = candle.candleNumber;
            this.name = candle.name;
            this.scent = candle.scent;
            this.color = candle.color;
            this.size = candle.size;
            this.price = candle.price;
            this.stockQuantity = candle.stockQuantity;
            return this;
        }

        public Candle build() {
            return new Candle(this);
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