package com.bxacosta.clientservice.dtos;

import com.bxacosta.clientservice.entities.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientRequestDTO extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 5821087248564911236L;

    private boolean active;
    private String password;
}
