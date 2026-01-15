package sn.fr.samagp.services;


import org.springframework.stereotype.Component;
import sn.fr.samagp.exceptions.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileDeleter {


    public void delete(Path target) {
        try {
            Files.deleteIfExists(target);
        } catch (IOException ex) {
            throw new FileStorageException(
                    "Erreur lors de la suppression du fichier", ex
            );
        }
    }
}
