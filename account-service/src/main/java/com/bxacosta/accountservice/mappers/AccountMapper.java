package com.bxacosta.accountservice.mappers;

import com.bxacosta.accountservice.dtos.AccountRequestDTO;
import com.bxacosta.accountservice.dtos.AccountResponseDTO;
import com.bxacosta.accountservice.entities.Account;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @SuppressWarnings("unused")
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountResponseDTO toResponseDTO(Account account);

    Account toEntity(AccountRequestDTO accountDTO);
}
