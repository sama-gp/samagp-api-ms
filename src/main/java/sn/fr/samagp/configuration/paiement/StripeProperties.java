package sn.fr.samagp.configuration.paiement;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.stripe")
public class StripeProperties {
    private String secretKey;
    private String publicKey;
    private String webhookSecret;
}