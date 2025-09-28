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

    // Payment validation methods
    public static boolean isValidCardNumber(String cardNumber) {
        if (isNullOrEmpty(cardNumber)) {
            return false;
        }
        // Remove spaces and check if it's 16 digits
        String cleanCardNumber = cardNumber.replaceAll("\\s", "");
        return cleanCardNumber.matches("^\\d{16}$");
    }

    public static boolean isValidExpiryDate(String expiryDate) {
        if (isNullOrEmpty(expiryDate)) {
            return false;
        }
        // Format: MM/YY
        return expiryDate.matches("^(0[1-9]|1[0-2])/([0-9]{2})$");
    }

    public static boolean isValidCvv(String cvv) {
        if (isNullOrEmpty(cvv)) {
            return false;
        }
        // CVV should be 3 digits
        return cvv.matches("^\\d{3}$");
    }

    public static boolean isValidNameOnCard(String nameOnCard) {
        if (isNullOrEmpty(nameOnCard)) {
            return false;
        }
        // Name should be at least 2 characters and contain only letters and spaces
        return nameOnCard.length() >= 2 && nameOnCard.matches("^[a-zA-Z\\s]+$");
    }

    public static String formatCardNumber(String cardNumber) {
        if (isNullOrEmpty(cardNumber)) {
            return "";
        }
        // Remove all non-digit characters
        String cleanCardNumber = cardNumber.replaceAll("\\D", "");
        
        // Format as XXXX XXXX XXXX XXXX
        if (cleanCardNumber.length() <= 4) {
            return cleanCardNumber;
        } else if (cleanCardNumber.length() <= 8) {
            return cleanCardNumber.substring(0, 4) + " " + cleanCardNumber.substring(4);
        } else if (cleanCardNumber.length() <= 12) {
            return cleanCardNumber.substring(0, 4) + " " + cleanCardNumber.substring(4, 8) + " " + cleanCardNumber.substring(8);
        } else {
            return cleanCardNumber.substring(0, 4) + " " + cleanCardNumber.substring(4, 8) + " " + 
                   cleanCardNumber.substring(8, 12) + " " + cleanCardNumber.substring(12, 16);
        }
    }

    public static String formatExpiryDate(String expiryDate) {
        if (isNullOrEmpty(expiryDate)) {
            return "";
        }
        // Remove all non-digit characters
        String cleanDate = expiryDate.replaceAll("\\D", "");
        
        if (cleanDate.length() <= 2) {
            return cleanDate;
        } else {
            return cleanDate.substring(0, 2) + "/" + cleanDate.substring(2, 4);
        }
    }
}
