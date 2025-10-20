package com.ocbc.ms.util;

/**
 * String utility class
 */
public class SpecUtil {

    private SpecUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Capitalizes the first character of the given string
     *
     * @param str the string to capitalize
     * @return the string with first character capitalized, or original string if null/empty
     */
    public static String capitalizeFirstChar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
