package sn.fr.samagp.services;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sn.fr.samagp.exceptions.FileStorageException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class FileWriter {

    public void write(MultipartFile file, Path target) {
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException(
                    "Error writing file", ex
            );
        }
    }
}
