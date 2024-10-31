package com.bxacosta.clientservice.dtos;

import com.bxacosta.clientservice.entities.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientResponseDTO extends Person implements Serializable {

    @Serial
    private static final long serialVersionUID = -4816407293052723533L;

    private Long id;
    private Integer age;
    private boolean active;
}
