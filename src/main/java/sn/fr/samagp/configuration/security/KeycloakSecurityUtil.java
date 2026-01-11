package sn.fr.samagp.configuration.security;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakSecurityUtil {

    private final KeycloakPropertiesConfig keycloakPropertiesConfig;
    private Keycloak keycloak;

    public Keycloak getKeycloakInstance() {
        if (keycloak == null) {
            KeycloakPropertiesConfig.Credentials credentials = keycloakPropertiesConfig.getCredentials();

            keycloak = KeycloakBuilder.builder()
                    .serverUrl(keycloakPropertiesConfig.getAuthServerUrl())
                    .realm(keycloakPropertiesConfig.getRealm())
                    .clientId(keycloakPropertiesConfig.getResource())
                    .grantType(credentials.getGrantType())
                    .username(credentials.getUsername())
                    .password(credentials.getPassword())
                    .build();
        }
        return keycloak;
    }
}
