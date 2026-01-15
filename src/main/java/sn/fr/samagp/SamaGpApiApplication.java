package sn.fr.samagp;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import sn.fr.samagp.configuration.security.KeycloakPropertiesConfig;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
		name = "Keycloak"
		, openIdConnectUrl = "${KEYCLOAK_EXTERNAL_URL:http://localhost:8090}/realms/samagp/.well-known/openid-configuration"
		, scheme = "bearer"
		, type = SecuritySchemeType.OPENIDCONNECT
		, in = SecuritySchemeIn.HEADER
)

@EnableConfigurationProperties(KeycloakPropertiesConfig.class)
public class SamaGpApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(SamaGpApiApplication.class, args);
	}

}

