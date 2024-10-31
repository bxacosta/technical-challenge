package com.bxacosta.accountservice.dtos;

import com.bxacosta.accountservice.enums.AccountTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class AccountRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2722878520067504763L;

    private Long clientId;
    private String number;
    private AccountTypeEnum type;
    private BigDecimal balance;
    private boolean active;
}
