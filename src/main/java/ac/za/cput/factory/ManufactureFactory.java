package ac.za.cput.factory;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.util.Helper;

/*
 * ManufactureFactory.java
 * Factory for Manufacture
 * Author: Anda Matomela
 * Date: 26 May 2025
 **/
public class ManufactureFactory {
    public static Manufacture createManufacture(String manufacturerName, String inventoryStock) {
        if (Helper.isNullOrEmpty(manufacturerName) || Helper.isNullOrEmpty(inventoryStock)) {
            return null;
        }
        int manufacturerNumber = Helper.generateIntId();

        return new Manufacture.Builder()
                .setManufacturerNumber(manufacturerNumber)
                .setManufacturerName(manufacturerName)
                .setInventoryStock(inventoryStock)
                .build();


    }




}