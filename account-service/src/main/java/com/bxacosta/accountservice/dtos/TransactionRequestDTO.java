package com.bxacosta.accountservice.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TransactionRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5896754264106545002L;

    private String accountNumber;
    private BigDecimal value;
}
