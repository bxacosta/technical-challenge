package com.bxacosta.clientservice.entities;

import com.bxacosta.clientservice.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDate;

@Data
@MappedSuperclass
public class Person {
    private String identification;
    private String name;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private LocalDate birthDate;
    private String address;
    private String phone;
}
