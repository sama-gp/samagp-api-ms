package sn.fr.samagp.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.services.inter.IClientService;

import java.util.UUID;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(AnnonceController.class);
    private final IClientService clientService;


    // Créer un nouveau client
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> createClient(@RequestBody ClientDTO clientDTO) {
        Client createdClient = clientService.syncClient(clientDTO);
        return ResponseEntity.status(201).body(createdClient);
    }
    @GetMapping("/details/{keycloakId}")
    public ResponseEntity<ClientDTO> getClientByKeycloakId(@PathVariable String keycloakId) {
        return ResponseEntity.ok(clientService.findByKeycloakId(keycloakId));
    }

    @PutMapping("/{keycloakId}")
    public ResponseEntity<ClientDTO> updateClient(
            @PathVariable String keycloakId,
            @RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.updateClient(keycloakId, clientDTO));
    }

    // Ajouter une adresse à un client
    @PostMapping("/{clientId}/addresses")
    public ResponseEntity<Client> addAddress(
            @PathVariable UUID clientId,
            @RequestBody Adresse address) {
        Client updatedClient = clientService.addAddressToClient(clientId, address);
        return ResponseEntity.ok(updatedClient);
    }

    @PostMapping("/{clientId}/phones")
    public ResponseEntity<Client> addPhone(
            @PathVariable UUID clientId,
            @RequestBody String phone) {
        Client updatedClient = clientService.addPhoneToClient(clientId, phone);
        return ResponseEntity.ok(updatedClient);
    }

}
