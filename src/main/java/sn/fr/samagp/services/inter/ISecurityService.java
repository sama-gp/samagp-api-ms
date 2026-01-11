package sn.fr.samagp.services.inter;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public interface ISecurityService {
    JwtAuthenticationToken getAuthentication();
    String getCurrentUserId();
    String getCurrentUserEmail();
}
