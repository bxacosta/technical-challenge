package com.bxacosta.accountservice.utils;

import com.bxacosta.accountservice.exeptions.DataValidationException;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public final class ValidationUtils {

    private ValidationUtils() {
    }

    public static void validateRequiredField(Object value, String name) {
        if (ObjectUtils.isEmpty(value) || (value instanceof String stringValue && !StringUtils.hasText(stringValue)))
            throw new DataValidationException(String.format(ErrorMessageConstants.REQUIRED_FIELD, name));
    }

    public static void validatePositiveValue(BigDecimal balance, String name) {
        if (!NumberUtils.isGreaterThanOrEqual(balance, BigDecimal.ZERO))
            throw new DataValidationException(String.format(ErrorMessageConstants.NEGATIVE_FIELD_VALUE, name));
    }
}
