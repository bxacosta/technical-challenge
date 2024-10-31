package com.bxacosta.accountservice.dtos;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class TransactionResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7019056087641806801L;

    private Long id;
    private Long accountId;
    private BigDecimal value;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private ZonedDateTime createdAt;
}
