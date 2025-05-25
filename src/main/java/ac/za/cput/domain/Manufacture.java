package ac.za.cput.domain;

/*
 * Manufacture.java
 * Domain for Manufacture
 * Author: Anda Matomela
 * Date: 26 May 2025
 **/
public class Manufacture {
    private int manufacturerNumber;
    private String manufacturerName;
    private String inventoryStock;


    private Manufacture(Builder builder) {
        this.manufacturerNumber = builder.manufacturerNumber;
        this.manufacturerName = builder.manufacturerName;
        this.inventoryStock = builder.inventoryStock;
    }

    public int getManufacturerNumber() { return manufacturerNumber; }
    public String getManufacturerName() { return manufacturerName; }
    public String getInventoryStock() { return inventoryStock; }


    public static class Builder {
        private int manufacturerNumber;
        private String manufacturerName;
        private String inventoryStock;

        public Builder setManufacturerNumber(int manufacturerNumber) {
            this.manufacturerNumber = manufacturerNumber;
            return this;
        }

        public Builder setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
            return this;
        }

        public Builder setInventoryStock(String inventoryStock) {
            this.inventoryStock = inventoryStock;
            return this;
        }

        public Manufacture build() {
            return new Manufacture(this);
        }
    }
}