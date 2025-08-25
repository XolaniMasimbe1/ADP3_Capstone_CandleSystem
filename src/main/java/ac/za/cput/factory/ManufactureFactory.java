package ac.za.cput.factory;

import ac.za.cput.domain.Manufacture;
import ac.za.cput.util.Helper;

public class ManufactureFactory {
    public static Manufacture createManufacture(String manufacturerName) {
        if (Helper.isNullOrEmpty(manufacturerName) ) {
            return null;
        }
        return new Manufacture.Builder()
                .setManufacturerName(manufacturerName)
                .build();
    }
}