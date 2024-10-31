package com.bxacosta.clientservice.dtos;

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
public class ClientTransactionReportDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3711752138222584703L;

    private String clientName;
    private String accountNumber;
    private String accountType;
    private boolean accountActive;
    private BigDecimal transactionValue;
    private BigDecimal initialBalance;
    private BigDecimal availableBalance;
    private ZonedDateTime transactionDate;
}
