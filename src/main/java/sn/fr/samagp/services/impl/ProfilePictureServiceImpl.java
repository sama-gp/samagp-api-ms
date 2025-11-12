package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.fr.samagp.exceptions.ResourceNotFoundException;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.services.inter.IProfilePictureService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfilePictureServiceImpl implements IProfilePictureService {

    private final ClientRepository clientRepository;
    private final FileStorageService fileStorageService;

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Override
    public String updateProfilePicture(MultipartFile file, String keycloakId) {
        // Validation du fichier
        validateFile(file);

        // Récupérer le client
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID Keycloak: " + keycloakId));

        // Supprimer l'ancienne photo si elle existe
        if (client.getProfilePictureUrl() != null) {
            fileStorageService.deleteFile(client.getProfilePictureUrl());
        }

        // Générer un nom de fichier unique avec l'ID du client
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = "user_" + keycloakId + "_" + UUID.randomUUID() + fileExtension;

        // Stocker la nouvelle photo avec le nom personnalisé
        String storedFilePath = fileStorageService.storeFile(file, "profile-pictures", uniqueFileName);

        // Mettre à jour l'URL dans la base de données
        client.setProfilePictureUrl(storedFilePath);
        clientRepository.save(client);

        return storedFilePath;
    }

    @Override
    public void deleteProfilePicture(String keycloakId) {
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID Keycloak: " + keycloakId));

        if (client.getProfilePictureUrl() != null) {
            fileStorageService.deleteFile(client.getProfilePictureUrl());
            client.setProfilePictureUrl(null);
            clientRepository.save(client);
        }
    }

    @Override
    public String getProfilePictureUrl(String keycloakId) {
        Client client = clientRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID Keycloak: " + keycloakId));
        return client.getProfilePictureUrl();
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("La taille du fichier ne doit pas dépasser 5MB");
        }

        String contentType = file.getContentType();
        if (!ALLOWED_CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Type de fichier non autorisé. Utilisez JPEG, PNG, GIF ou WebP");
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".jpg";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}