package com.bxacosta.accountservice;

import com.bxacosta.accountservice.controllers.AccountController;
import com.bxacosta.accountservice.dtos.AccountResponseDTO;
import com.bxacosta.accountservice.entities.Account;
import com.bxacosta.accountservice.mappers.AccountMapper;
import com.bxacosta.accountservice.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@ExtendWith(MockitoExtension.class)
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper accountMapper;

    private Account account;
    private AccountResponseDTO accountResponseDTO;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setId(1L);
        account.setNumber("123456");
        account.setBalance(BigDecimal.valueOf(1000));

        accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setId(1L);
        accountResponseDTO.setNumber("123456");
        accountResponseDTO.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    void getAllAccounts_shouldReturnListOfAccounts() throws Exception {
        when(accountService.getAll()).thenReturn(List.of(account));
        when(accountMapper.toResponseDTO(account)).thenReturn(accountResponseDTO);

        mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(accountResponseDTO.getId()))
                .andExpect(jsonPath("$.data[0].number").value(accountResponseDTO.getNumber()))
                .andExpect(jsonPath("$.data[0].balance").value(accountResponseDTO.getBalance()));
    }

    @Test
    void getAccountById_shouldReturnAccountWhenExists() throws Exception {
        when(accountService.getById(1L)).thenReturn(Optional.of(account));
        when(accountMapper.toResponseDTO(account)).thenReturn(accountResponseDTO);

        mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountResponseDTO.getId()))
                .andExpect(jsonPath("$.number").value(accountResponseDTO.getNumber()))
                .andExpect(jsonPath("$.balance").value(accountResponseDTO.getBalance()));
    }

    @Test
    void getAccountById_shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        when(accountService.getById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}