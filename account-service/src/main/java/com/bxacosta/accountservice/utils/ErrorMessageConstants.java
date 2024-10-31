package com.bxacosta.accountservice.utils;

public final class ErrorMessageConstants {

    public static String RESOURCE_NOT_FOUND = "%s not found";
    public static String REQUIRED_FIELD = "Field '%s' is required";
    public static String NEGATIVE_FIELD_VALUE = "Value of '%s' can't be less than zero";
    public static String NON_UNIQUE_VALUE = "Value of '%s' already exists";
    public static String RESOURCE_NOT_FOUND_BY_FIELD = "%s not found for '%s' field value";
    public static String INSUFFICIENT_BALANCE = "Insufficient balance";
    public static String NON_ZERO_VALUE = "Value of '%s' must be non-zero";

    private ErrorMessageConstants() {
    }
}
