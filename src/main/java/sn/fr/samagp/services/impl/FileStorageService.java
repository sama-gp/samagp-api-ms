package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.fr.samagp.services.FileDeleter;
import sn.fr.samagp.services.FilePathFormatter;
import sn.fr.samagp.services.FileWriter;
import sn.fr.samagp.utils.DirectoryInitializer;
import sn.fr.samagp.utils.PathResolver;

import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FileStorageService {


    private final PathResolver pathResolver;
    private final DirectoryInitializer directoryInitializer;
    private final FileWriter fileWriter;
    private final FilePathFormatter formatter;
    private final FileDeleter fileDeleter;

    /**
     * Stocke un fichier uploadé dans un répertoire spécifique avec un nom personnalisé.
     *
     * <p>Cette méthode gère le processus complet de sauvegarde d'un fichier en effectuant
     * les opérations suivantes dans l'ordre :
     * <ol>
     *   <li>Résolution du chemin du répertoire de destination</li>
     *   <li>Création du répertoire s'il n'existe pas</li>
     *   <li>Écriture du fichier sur le disque</li>
     *   <li>Formatage et retour du chemin d'accès final</li>
     * </ol>
     * </p>
     *
     * @throws IllegalArgumentException si l'un des paramètres est invalide
     * @throws SecurityException si l'accès au répertoire est refusé
     */
    public String storeFile(MultipartFile file, String subDirectory, String customFileName) {

        Path directory = pathResolver.resolve(subDirectory);
        directoryInitializer.ensureExists(directory);

        Path target = directory.resolve(customFileName);
        fileWriter.write(file, target);

        return formatter.format(subDirectory, customFileName);
    }

    public void deleteFile(String filePath) {
        Path target = pathResolver.resolve(filePath);
        fileDeleter.delete(target);
    }

    // Gardez aussi l'ancienne méthode pour compatibilité
    public String storeFile(MultipartFile file, String subDirectory) {
        return storeFile(file, subDirectory, file.getOriginalFilename());
    }

}