package sn.fr.samagp.configuration.security;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak")
@Data
public class KeycloakPropertiesConfig {

    private String realm;
    private String authServerUrl;
    private String resource;
    private Credentials credentials;

    @Data
    public static class Credentials {
        private String grantType;
        private String username;
        private String password;
    }
}
