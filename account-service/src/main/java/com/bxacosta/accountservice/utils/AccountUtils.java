package com.bxacosta.accountservice.utils;

import com.bxacosta.accountservice.entities.Account;
import com.bxacosta.accountservice.enums.OperationTypeEnum;

public final class AccountUtils {

    private AccountUtils() {
    }

    public static void validate(OperationTypeEnum type, Account account) {
        if (OperationTypeEnum.CREATE.equals(type) || OperationTypeEnum.UPDATE.equals(type)) {
            ValidationUtils.validateRequiredField(account.getType(), "type");
            ValidationUtils.validateRequiredField(account.getNumber(), "number");
            ValidationUtils.validateRequiredField(account.getClientId(), "clientId");
            ValidationUtils.validateRequiredField(account.getBalance(), "balance");
            ValidationUtils.validatePositiveValue(account.getBalance(), "balance");
        }
    }
}
