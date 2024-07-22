package com.library.book.utils;

import com.library.book.exceptions.BadRequestException;

public class ValidationUtils {
    public static Long isValid(String convertedValue) {
        Long value;
        try {
            value = Long.parseLong(convertedValue);
        }catch (NumberFormatException e) {
            throw new BadRequestException("Invalid format : " + convertedValue);
        }
        return value;
    }
}
