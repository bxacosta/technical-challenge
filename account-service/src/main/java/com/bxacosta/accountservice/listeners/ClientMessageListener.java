package com.bxacosta.accountservice.listeners;

import com.bxacosta.accountservice.config.RabbitMQConfig;
import com.bxacosta.accountservice.dtos.AccountTransactionReportDTO;
import com.bxacosta.accountservice.dtos.ClientDeactivatedMessageDTO;
import com.bxacosta.accountservice.dtos.ListResponseDTO;
import com.bxacosta.accountservice.dtos.TransactionReportMessageDTO;
import com.bxacosta.accountservice.services.AccountService;
import com.bxacosta.accountservice.services.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientMessageListener {

    private final AccountService accountService;
    private final AccountTransactionService accountTransactionService;

    @RabbitListener(queues = RabbitMQConfig.CLIENT_STATUS_QUEUE_NAME)
    public void onClientStatusUpdate(ClientDeactivatedMessageDTO message) {
        log.info("Received message from queue: {}, message: {}", RabbitMQConfig.CLIENT_STATUS_QUEUE_NAME, message);

        try {
            accountService.deactivateByClientId(message.id());
        } catch (Exception e) {
            log.error("Error while deactivating client accounts: {}", e.getMessage(), e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.CLIENT_RPC_QUEUE_NAME)
    public ListResponseDTO<AccountTransactionReportDTO> onClientRequest(TransactionReportMessageDTO message) {
        log.info("Received message from queue: {}, message: {}", RabbitMQConfig.CLIENT_RPC_QUEUE_NAME, message);

        try {
            return ListResponseDTO.from(accountTransactionService.getAccountTransactions(
                    message.clientId(),
                    message.start(),
                    message.end()));
        } catch (Exception e) {
            log.error("Error querying transaction report: {}", e.getMessage(), e);
        }

        return ListResponseDTO.empty();
    }
}
