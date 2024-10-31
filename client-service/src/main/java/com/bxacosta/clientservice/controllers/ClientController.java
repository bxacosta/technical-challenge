package com.bxacosta.clientservice.controllers;

import com.bxacosta.clientservice.dtos.ClientRequestDTO;
import com.bxacosta.clientservice.dtos.ClientResponseDTO;
import com.bxacosta.clientservice.dtos.ClientTransactionReportDTO;
import com.bxacosta.clientservice.dtos.ListResponseDTO;
import com.bxacosta.clientservice.entities.Client;
import com.bxacosta.clientservice.mappers.ClientMapper;
import com.bxacosta.clientservice.services.ClientService;
import com.bxacosta.clientservice.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientMapper clientMapper;
    private final ClientService clientService;
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ListResponseDTO<ClientResponseDTO>> getAll() {
        List<ClientResponseDTO> clients = clientService.getAll().stream()
                .map(clientMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(ListResponseDTO.from(clients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getById(@PathVariable Long id) {
        return clientService.getById(id)
                .map(clientMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@RequestBody ClientRequestDTO client) {
        Client created = clientService.create(clientMapper.toEntity(client));
        return ResponseEntity.ok(clientMapper.toResponseDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @RequestBody ClientRequestDTO client) {
        Client updatedClient = clientService.update(id, clientMapper.toEntity(client));
        return ResponseEntity.ok(clientMapper.toResponseDTO(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clientService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/report")
    public ResponseEntity<ListResponseDTO<ClientTransactionReportDTO>> getTransactionReport(
            @PathVariable Long id,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(ListResponseDTO.from(reportService.geTransactionsByClientId(
                id,
                startDate,
                endDate,
                ZoneId.of("UTC")
        )));
    }
}


