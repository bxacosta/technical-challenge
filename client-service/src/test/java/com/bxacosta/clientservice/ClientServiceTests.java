package com.bxacosta.clientservice;

import com.bxacosta.clientservice.entities.Client;
import com.bxacosta.clientservice.repositories.JpaClientRepository;
import com.bxacosta.clientservice.services.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {

    @Mock
    private JpaClientRepository jpaClientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("Jose Lema");
    }

    @Test
    @DisplayName("createClient_shouldReturnCreatedClient")
    void createClient_shouldReturnCreatedClient() {
        when(jpaClientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.create(client);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Jose Lema", result.getName());
    }


    @Test
    @DisplayName("getClientById_shouldReturnClientWhenExists")
    void getClientById_shouldReturnClientWhenExists() {
        when(jpaClientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("Jose Lema", result.get().getName());
    }
}
