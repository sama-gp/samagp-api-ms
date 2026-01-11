package sn.fr.samagp.configuration;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "file")
@Validated
@Getter
@Setter
public class FileStorageProperties {
    @NotNull
    private Path uploadDir;
}
