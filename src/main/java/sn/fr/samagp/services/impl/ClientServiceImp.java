package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sn.fr.samagp.controller.response.ClientResponse;
import sn.fr.samagp.exceptions.ResourceNotFoundException;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.dto.ClientDocumentsDTO;
import sn.fr.samagp.repository.dto.FollowDTO;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Profile;
import sn.fr.samagp.repository.model.TypePieces;
import sn.fr.samagp.services.inter.IClientService;
import sn.fr.samagp.services.inter.ISecurityService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements IClientService {

    private static final Logger log = LoggerFactory.getLogger(ClientServiceImp.class);
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ISecurityService securityService;
    private final DocumentStorageService documentStorageService;


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
                    // Sauvegarder l'ancien profil pour vérifier les changements
                    Profile oldProfile = existing.getProfile();

                    // Mise à jour des champs de base
                    if (dto.getFirstName() != null) existing.setFirstName(dto.getFirstName());
                    if (dto.getLastName() != null) existing.setLastName(dto.getLastName());
                    if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
                    if (dto.getProfile() != null) existing.setProfile(dto.getProfile());

                    // Mise à jour des documents d'identité - SEULEMENT si GP
                    if (dto.getProfile() == Profile.GP) {
                        if (dto.getTypePieces() != null) existing.setTypePieces(dto.getTypePieces());
                        if (dto.getPiecesRecto() != null) existing.setPiecesRecto(dto.getPiecesRecto());
                        if (dto.getPiecesVerso() != null) existing.setPiecesVerso(dto.getPiecesVerso());
                        if (dto.getNinea() != null) existing.setNinea(dto.getNinea());

                        // Si changement de profil vers GP ou modification des documents, marquer comme en attente
                        if (oldProfile != Profile.GP || dto.getTypePieces() != null ||
                                dto.getPiecesRecto() != null || dto.getPiecesVerso() != null ||
                                dto.getNinea() != null) {
                            existing.markAsPendingValidation();
                        }
                    } else {
                        // Si passage de GP à CLIENT, on peut conserver les documents mais le compte n'est plus validé
                        if (oldProfile == Profile.GP) {
                            existing.markAsPendingValidation();
                        }
                    }

                    // Mise à jour des téléphones
                    if (dto.getPhone() != null) {
                        existing.getPhone().clear();
                        existing.getPhone().addAll(dto.getPhone());
                    }

                    // Mise à jour des adresses
                    if (dto.getAddress() != null) {
                        existing.getAddress().clear();
                        existing.getAddress().addAll(dto.getAddress());
                    }

                    // Validation des données pour les GP
                    if (existing.getProfile() == Profile.GP) {
                        validateGPRequirements(existing);
                    }

                    Client updatedClient = clientRepository.save(existing);
                    return clientMapper.toDto(updatedClient);

                })
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID: " + keycloakId));
    }

    // Méthode de validation pour les GP
    private void validateGPRequirements(Client client) {
        if (client.getProfile() != Profile.GP) return;

        if (client.getTypePieces() == null) {
            throw new IllegalArgumentException("Le type de pièce d'identité est obligatoire pour les professionnels");
        }

        if (client.getPiecesRecto() == null || client.getPiecesRecto().isEmpty()) {
            throw new IllegalArgumentException("Le recto de la pièce d'identité est obligatoire pour les professionnels");
        }

        if (client.getTypePieces() == TypePieces.CARTE_NATIONALE &&
                (client.getPiecesVerso() == null || client.getPiecesVerso().isEmpty())) {
            throw new IllegalArgumentException("Le verso de la carte nationale est obligatoire");
        }

        if (client.getNinea() == null || client.getNinea().isEmpty()) {
            throw new IllegalArgumentException("Le NINEA est obligatoire pour les professionnels");
        }
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

    @Override
    public FollowDTO followClient(String followedClientId) {
        String followerKeycloakId = securityService.getCurrentUserId();
        Client follower = clientRepository.findByKeycloakId(followerKeycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client follower not found"));
        Client followed = clientRepository.findById(UUID.fromString(followedClientId))
                .orElseThrow(() -> new ResourceNotFoundException("Client to follow not found"));

        follower.follow(followed);
        clientRepository.save(follower);

        return new FollowDTO(
                follower.getKeycloakId(),
                followed.getKeycloakId(),
                true,
                followed.getFollowers().size()
        );
    }

    @Override
    public FollowDTO unfollowClient(String followedClientId) {
        String followerKeycloakId = securityService.getCurrentUserId();
        Client follower = clientRepository.findByKeycloakId(followerKeycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client follower not found"));
        Client followed = clientRepository.findById(UUID.fromString(followedClientId))
                .orElseThrow(() -> new ResourceNotFoundException("Client to unfollow not found"));

        follower.unfollow(followed);
        clientRepository.save(follower);

        return new FollowDTO(
                follower.getKeycloakId(),
                followed.getKeycloakId(),
                false,
                followed.getFollowers().size()
        );
    }

    @Override
    public boolean isFollowing(String followedClientId) {
        String followerKeycloakId = securityService.getCurrentUserId();
        Client follower = clientRepository.findByKeycloakId(followerKeycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client follower not found"));
        Client followed = clientRepository.findById(UUID.fromString(followedClientId))
                .orElseThrow(() -> new ResourceNotFoundException("Client to check not found"));

        return follower.getFollowing().contains(followed);
    }

    @Override
    public int getFollowersCount(String clientId) {
        Client client = clientRepository.findByKeycloakId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        return client.getFollowers().size();
    }

    @Override
    public List<ClientResponse> getRecentClients(int limit) {
        // Récupère les clients triés par date de création décroissante
        List<Client> recentClients = clientRepository.findAll(
                PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"))
        ).getContent();
        return recentClients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientResponse> getAllClients() {
        // Récupère tous les clients triés par date de création décroissante
        List<Client> allClients = clientRepository.findAll(
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return allClients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
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

    @Override
    public ClientDTO validateClient(String keycloakId) {
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID: " + keycloakId));

        client.validate();
        Client validatedClient = clientRepository.save(client);
        return clientMapper.toDto(validatedClient);
    }

    @Override
    public ClientDTO rejectClient(String keycloakId) {
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID: " + keycloakId));

        client.rejectValidation();
        Client rejectedClient = clientRepository.save(client);
        return clientMapper.toDto(rejectedClient);
    }

    @Override
    public List<ClientResponse> getPendingValidationClients() {
        List<Client> pendingClients = clientRepository.findByIsValidFalseAndHasAllDocuments();
        return pendingClients.stream()
                .map(clientMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean canCreateAnnonce(String keycloakId) {
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID: " + keycloakId));

        return client.canCreateAnnonces();
    }

    @Override
    public ClientDTO updateClientDocuments(String keycloakId, ClientDocumentsDTO documentsDTO) {

        log.info("Début updateClientDocuments pour client: {}", keycloakId);


        return clientRepository.findByKeycloakId(keycloakId)
                .map(existing -> {
                    // Mise à jour des métadonnées
                    log.info("Client trouvé: {}", existing.getEmail());

                    if (documentsDTO.getTypePieces() != null) {
                        log.info("Mise à jour typePieces: {}", documentsDTO.getTypePieces());
                        existing.setTypePieces(documentsDTO.getTypePieces());
                    }
                    if (documentsDTO.getNinea() != null) {
                        log.info("Mise à jour ninea: {}", documentsDTO.getNinea());
                        existing.setNinea(documentsDTO.getNinea());
                    }

                    // CORRECTION : Upload et mise à jour des fichiers RECT0
                    if (documentsDTO.getRectoFile() != null && !documentsDTO.getRectoFile().isEmpty()) {
                        log.info("Upload rectoFile: {} - taille: {}",
                                documentsDTO.getRectoFile().getOriginalFilename(),
                                documentsDTO.getRectoFile().getSize());
                        // Supprimer l'ancien fichier recto s'il existe
                        if (existing.getPiecesRecto() != null) {
                            documentStorageService.deleteDocument(existing.getPiecesRecto());
                        }
                        // Upload du nouveau fichier recto
                        String rectoPath = documentStorageService.storeDocument(
                                documentsDTO.getRectoFile(), "pieces", keycloakId);
                        existing.setPiecesRecto(rectoPath);
                    }

                    // CORRECTION : Upload et mise à jour des fichiers VERSO
                    if (documentsDTO.getVersoFile() != null && !documentsDTO.getVersoFile().isEmpty()) {
                        // Supprimer l'ancien fichier verso s'il existe
                        if (existing.getPiecesVerso() != null) {
                            documentStorageService.deleteDocument(existing.getPiecesVerso());
                        }
                        // Upload du nouveau fichier verso
                        String versoPath = documentStorageService.storeDocument(
                                documentsDTO.getVersoFile(), "pieces", keycloakId);
                        existing.setPiecesVerso(versoPath);
                    }

                    // Si GP et modification des documents, marquer comme en attente
                    if (existing.getProfile() == Profile.GP) {
                        existing.markAsPendingValidation();

                        // Validation des documents pour les GP
                        validateGPRequirements(existing);
                    }

                    Client updatedClient = clientRepository.save(existing);
                    log.info("Client sauvegardé avec succès");
                    return clientMapper.toDto(updatedClient);

                })
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID: " + keycloakId));
    }


}
