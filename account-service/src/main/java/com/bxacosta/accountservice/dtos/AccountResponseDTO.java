package com.bxacosta.accountservice.dtos;

import com.bxacosta.accountservice.entities.Account;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountResponseDTO extends Account {

    @Serial
    private static final long serialVersionUID = -3994871686846690757L;
}
