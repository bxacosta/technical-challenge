package com.bxacosta.clientservice.services;

import com.bxacosta.clientservice.config.RabbitMQConfig;
import com.bxacosta.clientservice.dtos.ClientDeactivatedMessageDTO;
import com.bxacosta.clientservice.entities.Client;
import com.bxacosta.clientservice.exeptions.ResourceNotFoundException;
import com.bxacosta.clientservice.repositories.JpaClientRepository;
import com.bxacosta.clientservice.utils.ClientUtils;
import com.bxacosta.clientservice.utils.ErrorMessageConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final RabbitTemplate rabbitTemplate;
    private final JpaClientRepository jpaClientRepository;

    public Optional<Client> getById(Long id) {
        return jpaClientRepository.findById(id);
    }

    public List<Client> getAll() {
        return jpaClientRepository.findAll();
    }

    public Client create(Client client) {
        ClientUtils.validate(client);

        client.setId(null);
        client.setActive(true);
        client.setUpdatedAt(ZonedDateTime.now());
        client.setCreatedAt(ZonedDateTime.now());
        return jpaClientRepository.save(client);
    }

    public Client update(Long id, Client client) {
        ClientUtils.validate(client);

        Client savedClient = jpaClientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessageConstants.RESOURCE_NOT_FOUND, "Client")));

        client.setId(savedClient.getId());
        client.setUpdatedAt(ZonedDateTime.now());
        client.setCreatedAt(savedClient.getCreatedAt());

        return jpaClientRepository.save(client);
    }

    @Transactional
    public void deactivate(Long id) {
        jpaClientRepository.updateActive(id, false);
        this.sendDeactivateClientMessage(id);
    }

    private void sendDeactivateClientMessage(Long id) {
        ClientDeactivatedMessageDTO message = new ClientDeactivatedMessageDTO(id);
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.CLIENT_EXCHANGE_NAME, RabbitMQConfig.CLIENT_STATUS_UPDATE_ROUTING_KEY, message);
        } catch (Exception e) {
            log.error("Error sending client deactivation message", e);
        }
    }
}
