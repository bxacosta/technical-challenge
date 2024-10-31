package com.bxacosta.clientservice;

import com.bxacosta.clientservice.config.RabbitMQConfig;
import com.bxacosta.clientservice.dtos.ClientTransactionReportDTO;
import com.bxacosta.clientservice.dtos.ListResponseDTO;
import com.bxacosta.clientservice.dtos.TransactionReportMessageDTO;
import com.bxacosta.clientservice.entities.Client;
import com.bxacosta.clientservice.exeptions.ResourceNotFoundException;
import com.bxacosta.clientservice.services.ClientService;
import com.bxacosta.clientservice.services.ReportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ReportService reportService;

    private Client client;
    private ClientTransactionReportDTO transaction;
    private LocalDate startDate;
    private LocalDate endDate;
    private ZoneId zoneId;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("Test Client");

        transaction = new ClientTransactionReportDTO();
        transaction.setAccountNumber("1234");
        transaction.setTransactionValue(BigDecimal.valueOf(100));

        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 12, 31);
        zoneId = ZoneId.of("UTC");
    }

    @Test
    @DisplayName("geTransactionsByClientId_shouldReturnTransactionsWithClientName")
    void geTransactionsByClientId_shouldReturnTransactionsWithClientName() {
        when(clientService.getById(client.getId())).thenReturn(Optional.of(client));
        when(rabbitTemplate.convertSendAndReceiveAsType(
                eq(RabbitMQConfig.CLIENT_EXCHANGE_NAME),
                eq(RabbitMQConfig.CLIENT_RPC_REPORT_ROUTING_KEY),
                any(TransactionReportMessageDTO.class),
                any(ParameterizedTypeReference.class)
        )).thenReturn(new ListResponseDTO<>(List.of(transaction)));

        List<ClientTransactionReportDTO> result = reportService.geTransactionsByClientId(
                client.getId(), startDate, endDate, zoneId
        );

        assertEquals(1, result.size());
        assertEquals("Test Client", result.getFirst().getClientName());
        assertEquals(transaction.getAccountNumber(), result.getFirst().getAccountNumber());
        assertEquals(transaction.getTransactionValue(), result.getFirst().getTransactionValue());
    }

    @Test
    @DisplayName("geTransactionsByClientId_shouldThrowResourceNotFoundExceptionForInvalidClientId")
    void geTransactionsByClientId_shouldThrowResourceNotFoundExceptionForInvalidClientId() {
        when(clientService.getById(client.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> reportService.geTransactionsByClientId(
                client.getId(),
                startDate,
                endDate,
                zoneId
        ));

        verify(clientService, times(1)).getById(client.getId());
    }
}
