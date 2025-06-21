package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sn.fr.samagp.exceptions.ResourceNotFoundException;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Profile;
import sn.fr.samagp.services.inter.IClientService;
import sn.fr.samagp.services.inter.ISecurityService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ISecurityService securityService;

    @Override
    public Client syncClient(ClientDTO dto) {
        JwtAuthenticationToken authentication = securityService.getAuthentication();
        Jwt jwt = authentication != null ? authentication.getToken() : null;

        return clientRepository.findByEmail(dto.getEmail())
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setPhone(dto.getPhone());
                    existing.setAddress(dto.getAddress());
                    existing.setProfile(dto.getProfile());
                    return clientRepository.save(existing);
                })
                .orElseGet(() -> createNewClient(dto,jwt));
    }

    @Override
    public ClientDTO updateClient(String keycloakId, ClientDTO dto) {
        return clientRepository.findByKeycloakId(keycloakId)
                .map(existing -> {
                    // Mise à jour des champs de base
                    if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
                    if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
                    if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
                    if (dto.getProfile() != null) existing.setProfile(dto.getProfile());

                    // Mise à jour des téléphones (remplacement complet)
                    if (dto.getPhone() != null) {
                        existing.getPhone().clear();
                        existing.getPhone().addAll(dto.getPhone());
                    }

                    // Mise à jour des adresses (remplacement complet)
                    if (dto.getAddress() != null) {
                        existing.getAddress().clear();
                        existing.getAddress().addAll(dto.getAddress());
                    }

                    Client updatedClient = clientRepository.save(existing);
                    return clientMapper.toDto(updatedClient);

                })
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID: " + keycloakId));
    }

    @Override
    public Client addAddressToClient(UUID clientId, Adresse address) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"));

        client.getAddress().add(address);
        return clientRepository.save(client);
    }

    @Override
    public Client addPhoneToClient(UUID clientId, String phone) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé"));

        client.getPhone().add(phone);
        return clientRepository.save(client);
    }

    @Override
    public ClientDTO findByKeycloakId(String keycloakId) {
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID Keycloak: " + keycloakId));
        return clientMapper.toDto(client);
    }


    private Client createNewClient(ClientDTO dto, Jwt jwt) {
        Client newClient = new Client();
        newClient.setEmail(dto.getEmail());
        newClient.setFirstName(dto.getFirstName());
        newClient.setLastName(dto.getLastName());
        newClient.setPhone(dto.getPhone());
        newClient.setAddress(dto.getAddress());
        newClient.setProfile(dto.getProfile() != null ? dto.getProfile() : Profile.USER);
        newClient.setKeycloakId(dto.getKeycloakId());

        // Récupération du createdTimestamp depuis le JWT
        if (jwt != null && jwt.getClaim("createdTimestamp") != null) {
            long createdTimestamp = jwt.getClaim("createdTimestamp");
            LocalDateTime keycloakCreatedAt = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(createdTimestamp),
                    ZoneId.systemDefault()
            );
            newClient.setCreatedAt(keycloakCreatedAt);
        } else {
            newClient.setCreatedAt(LocalDateTime.now());
        }

        return clientRepository.save(newClient);
    }

    private String getFirstName(String fullName) {
        return fullName != null ? fullName.split(" ")[0] : "";
    }

    private String getLastName(String fullName) {
        String[] parts = fullName != null ? fullName.split(" ") : new String[0];
        return parts.length > 1 ? parts[1] : "";
    }
}
