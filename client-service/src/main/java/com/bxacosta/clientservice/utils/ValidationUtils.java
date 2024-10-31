package com.bxacosta.clientservice.utils;


import com.bxacosta.clientservice.exeptions.DataValidationException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateRequiredField(Object value, String name) {
        if (ObjectUtils.isEmpty(value) || (value instanceof String stringValue && !StringUtils.hasText(stringValue)))
            throw new DataValidationException(String.format(ErrorMessageConstants.REQUIRED_FIELD, name));
    }
}
