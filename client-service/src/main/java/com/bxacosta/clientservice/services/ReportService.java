package com.bxacosta.clientservice.services;

import com.bxacosta.clientservice.config.RabbitMQConfig;
import com.bxacosta.clientservice.dtos.ClientTransactionReportDTO;
import com.bxacosta.clientservice.dtos.ListResponseDTO;
import com.bxacosta.clientservice.dtos.TransactionReportMessageDTO;
import com.bxacosta.clientservice.entities.Client;
import com.bxacosta.clientservice.exeptions.ResourceNotFoundException;
import com.bxacosta.clientservice.utils.ErrorMessageConstants;
import com.bxacosta.clientservice.utils.ReportUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ClientService clientService;
    private final RabbitTemplate rabbitTemplate;

    public List<ClientTransactionReportDTO> geTransactionsByClientId(
            Long clientId,
            LocalDate startDate,
            LocalDate endDate,
            ZoneId zoneId
    ) {
        ReportUtils.validate(clientId, startDate, endDate, zoneId);

        Client client = clientService.getById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessageConstants.RESOURCE_NOT_FOUND, "Client")));

        ZonedDateTime startDateTime = ZonedDateTime.of(startDate, LocalTime.MIN, zoneId);
        ZonedDateTime endDateTime = ZonedDateTime.of(endDate, LocalTime.MAX, zoneId);

        List<ClientTransactionReportDTO> transactions = Optional
                .ofNullable(rabbitTemplate.convertSendAndReceiveAsType(
                        RabbitMQConfig.CLIENT_EXCHANGE_NAME,
                        RabbitMQConfig.CLIENT_RPC_REPORT_ROUTING_KEY,
                        new TransactionReportMessageDTO(clientId, startDateTime, endDateTime),
                        new ParameterizedTypeReference<ListResponseDTO<ClientTransactionReportDTO>>() {
                        }))
                .map(ListResponseDTO::getData)
                .orElse(new ArrayList<>());

        transactions.forEach(transaction -> transaction.setClientName(client.getName()));

        return transactions;
    }
}
