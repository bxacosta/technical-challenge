package com.bxacosta.accountservice.entities;

import com.bxacosta.accountservice.enums.AccountTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = -8641361956756017405L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String number;
    @Enumerated(EnumType.STRING)
    private AccountTypeEnum type;
    private BigDecimal balance;
    private boolean active;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
