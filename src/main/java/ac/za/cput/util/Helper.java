package ac.za.cput.util;
/*
 * Helper.java
 *  Helper
 * Author: Xolani Masimbe
 * Student Number: 222410817
 * Date: 09 June 2025
 **/
public class Helper {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }

    public static boolean isValidStoreNumber(String storeNumber) {
        if (isNullOrEmpty(storeNumber)) {
            return false;
        }
        String storeNumberRegex = "^[A-Z]{2}\\d{4}$"; // Example: AB1234
        return storeNumber.matches(storeNumberRegex);
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (isNullOrEmpty(phoneNumber)) {
            return false;
        }
        String phoneRegex = "^\\+?[0-9]{10,15}$"; // Allows optional '+' and 10 to 15 digits
        return phoneNumber.matches(phoneRegex);
    }
    public static boolean localDateIsValid(String date) {
        if (isNullOrEmpty(date)) {
            return false;
        }
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$"; // Format: YYYY-MM-DD
        return date.matches(dateRegex);
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    public static boolean isAmountValid(String amount){
        if (isNullOrEmpty(amount)) {
            return false;
        }
        try {
            double value = Double.parseDouble(amount);
            return value >= 0; // Assuming valid amounts are non-negative
        } catch (NumberFormatException e) {
            return false; // Not a valid number
        }
    }
}
