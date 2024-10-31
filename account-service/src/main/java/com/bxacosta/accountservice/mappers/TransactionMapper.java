package com.bxacosta.accountservice.mappers;

import com.bxacosta.accountservice.dtos.TransactionRequestDTO;
import com.bxacosta.accountservice.dtos.TransactionResponseDTO;
import com.bxacosta.accountservice.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @SuppressWarnings("unused")
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionResponseDTO toResponseDTO(Transaction account);

    Transaction toEntity(TransactionRequestDTO accountDTO);
}
