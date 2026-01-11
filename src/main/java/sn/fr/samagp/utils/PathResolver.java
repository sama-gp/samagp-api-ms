package sn.fr.samagp.utils;


import org.springframework.stereotype.Component;
import sn.fr.samagp.configuration.FileStorageProperties;

import java.nio.file.Path;

@Component
public class PathResolver {

    private final FileStorageProperties properties;

    public PathResolver(FileStorageProperties properties) {
        this.properties = properties;
    }

    public Path resolve(String subDirectory) {
        return properties.getUploadDir().resolve(subDirectory).normalize();
    }
}
