package com.bxacosta.accountservice.utils;

import java.math.BigDecimal;

public final class NumberUtils {

    private NumberUtils() {
    }

    public static boolean isGreaterThanOrEqual(BigDecimal value1, BigDecimal value2) {
        return value1.compareTo(value2) >= 0;
    }
}
