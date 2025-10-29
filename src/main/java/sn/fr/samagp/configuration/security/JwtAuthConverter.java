package sn.fr.samagp.configuration.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        log.info("[JwtAuthConverter] Conversion du token en AuthenticationToken...");
        log.debug("Claims JWT : {}", jwt.getClaims());

        Collection<GrantedAuthority> roles = extractAuthorities(jwt);
        log.info("Rôles extraits : {}", roles);

        return new JwtAuthenticationToken(jwt, roles);
    }

    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        log.info("🔍 Extraction des rôles depuis le token...");

        if (jwt.getClaim("realm_access") == null) {
            log.warn("Aucun claim 'realm_access' trouvé dans le token !");
            return new ArrayList<>();
        }

        try {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            ObjectMapper mapper = new ObjectMapper();

            List<String> keycloakRoles = mapper.convertValue(
                    realmAccess.get("roles"),
                    new TypeReference<List<String>>() {}
            );

            if (keycloakRoles == null || keycloakRoles.isEmpty()) {
                log.warn("Aucun rôle trouvé dans realm_access.roles !");
                return new ArrayList<>();
            }

            List<GrantedAuthority> roles = new ArrayList<>();
            for (String keycloakRole : keycloakRoles) {
                log.info("Rôle Keycloak détecté : {}", keycloakRole);
                roles.add(new SimpleGrantedAuthority(keycloakRole));
            }

            return roles;

        } catch (Exception e) {
            log.error("Erreur lors de l'extraction des rôles depuis le token : {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
