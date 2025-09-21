package ac.za.cput.domain;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Product {
    @Id
    private String productNumber;
    private String name;
    private double price;
    private int stockQuantity;

    private String scent;
    private String color;
    private String size;

    @Lob
    private byte[] imageData;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manufacturer_number", referencedColumnName = "manufacturerNumber")
    private Manufacture manufacturer;

    protected Product() {}

    private Product(Builder builder) {
        this.productNumber = builder.productNumber;
        this.name = builder.name;
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;
        this.scent = builder.scent;
        this.color = builder.color;
        this.size = builder.size;
        this.imageData = builder.imageData;
        this.manufacturer = builder.manufacturer;
    }

    // Getters
    public String getProductNumber() { return productNumber; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }
    public String getScent() { return scent; }
    public String getColor() { return color; }
    public String getSize() { return size; }
    public Manufacture getManufacturer() { return manufacturer; }
    
    // Internal method for image handling (used by controller)
    public byte[] getImageData() { return imageData; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productNumber, product.productNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNumber);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNumber='" + productNumber + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", scent='" + scent + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", imageData=" + (imageData != null ? "[" + imageData.length + " bytes]" : "null") +
                ", manufacturer=" + manufacturer +
                '}';
    }

    public static class Builder {
        private String productNumber;
        private String name;
        private double price;
        private int stockQuantity;
        private String scent;
        private String color;
        private String size;
        private byte[] imageData;
        private Manufacture manufacturer;

        public Builder setProductNumber(String productNumber) {
            this.productNumber = productNumber;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
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

        public Builder setImageData(byte[] imageData) {
            this.imageData = imageData;
            return this;
        }

        public Builder setManufacturer(Manufacture manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public Builder copy(Product product) {
            this.productNumber = product.productNumber;
            this.name = product.name;
            this.price = product.price;
            this.stockQuantity = product.stockQuantity;
            this.scent = product.scent;
            this.color = product.color;
            this.size = product.size;
            this.imageData = product.imageData;
            this.manufacturer = product.manufacturer;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}