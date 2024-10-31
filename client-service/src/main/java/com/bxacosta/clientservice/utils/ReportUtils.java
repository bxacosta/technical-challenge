package com.bxacosta.clientservice.utils;

import com.bxacosta.clientservice.exeptions.DataValidationException;

import java.time.LocalDate;
import java.time.ZoneId;

public final class ReportUtils {

    private ReportUtils() {
    }

    public static void validate(
            Long clientId,
            LocalDate startDate,
            LocalDate endDate,
            ZoneId zoneId
    ) {
        ValidationUtils.validateRequiredField(clientId, "clientId");
        ValidationUtils.validateRequiredField(startDate, "startDate");
        ValidationUtils.validateRequiredField(endDate, "endDate");
        ValidationUtils.validateRequiredField(zoneId, "zoneId");

        if (startDate.isAfter(endDate))
            throw new DataValidationException(ErrorMessageConstants.INVALID_DATE_RANGE);
    }
}
