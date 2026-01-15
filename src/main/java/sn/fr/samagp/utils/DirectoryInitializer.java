package sn.fr.samagp.utils;


import org.springframework.stereotype.Component;
import sn.fr.samagp.exceptions.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class DirectoryInitializer {


    public void ensureExists(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException ex) {
            throw new FileStorageException(
                    "Unable to create storage directory", ex
            );
        }
    }
}
