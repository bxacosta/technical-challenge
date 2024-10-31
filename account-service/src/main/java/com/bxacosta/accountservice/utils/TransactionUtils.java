package com.bxacosta.accountservice.utils;

import com.bxacosta.accountservice.entities.Transaction;
import com.bxacosta.accountservice.enums.OperationTypeEnum;
import com.bxacosta.accountservice.exeptions.DataValidationException;

import java.math.BigDecimal;

public final class TransactionUtils {

    private TransactionUtils() {
    }

    public static void validate(OperationTypeEnum type, Transaction transaction, String accountNumber) {
        if (OperationTypeEnum.CREATE.equals(type)) {
            ValidationUtils.validateRequiredField(transaction.getValue(), "value");
            ValidationUtils.validateRequiredField(accountNumber, "accountNumber");

            if (transaction.getValue().equals(BigDecimal.ZERO)) {
                throw new DataValidationException(String.format(ErrorMessageConstants.NON_ZERO_VALUE, "value"));
            }
        }
    }
}
