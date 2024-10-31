package com.bxacosta.accountservice.dtos;

import com.bxacosta.accountservice.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionReportDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1060841470938748281L;

    private String accountNumber;
    private AccountTypeEnum accountType;
    private boolean accountActive;
    private BigDecimal transactionValue;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private ZonedDateTime transactionDate;
}
