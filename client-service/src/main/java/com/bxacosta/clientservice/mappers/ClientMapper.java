package com.bxacosta.clientservice.mappers;

import com.bxacosta.clientservice.dtos.ClientRequestDTO;
import com.bxacosta.clientservice.dtos.ClientResponseDTO;
import com.bxacosta.clientservice.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @SuppressWarnings("unused")
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Named("calculateAge")
    static Integer calculateAge(LocalDate birthDate) {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    Client toEntity(ClientRequestDTO accountDTO);

    @Mapping(source = "birthDate", target = "age", qualifiedByName = "calculateAge")
    ClientResponseDTO toResponseDTO(Client client);
}
