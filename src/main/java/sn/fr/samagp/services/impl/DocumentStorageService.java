package sn.fr.samagp.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Slf4j
@Service
public class DocumentStorageService {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    public String storeDocument(MultipartFile file, String subDirectory, String clientKeycloakId) {
        try {
            // Créer le répertoire s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir, "documents", subDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Générer un nom de fichier unique
            String originalFileName = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFileName);
            String fileName = String.format("client_%s_%s%s",
                    clientKeycloakId,
                    UUID.randomUUID().toString(),
                    fileExtension);

            Path filePath = uploadPath.resolve(fileName);

            // Sauvegarder le fichier
            Files.copy(file.getInputStream(), filePath);

            // Retourner le chemin relatif pour la base de données
            return Paths.get("documents", subDirectory, fileName).toString();

        } catch (IOException ex) {
            log.error("Erreur lors de l'upload du document: {}", ex.getMessage());
            throw new RuntimeException("Erreur lors de l'upload du document: " + ex.getMessage(), ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastDotIndex = fileName.lastIndexOf(".");
        return (lastDotIndex > 0) ? fileName.substring(lastDotIndex) : "";
    }

    public void deleteDocument(String filePath) {
        try {
            Path path = Paths.get(uploadDir, filePath);
            Files.deleteIfExists(path);
        } catch (IOException ex) {
            log.error("Erreur lors de la suppression du document: {}", ex.getMessage());
        }
    }
}