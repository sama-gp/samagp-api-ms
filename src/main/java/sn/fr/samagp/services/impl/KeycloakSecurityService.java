package sn.fr.samagp.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sn.fr.samagp.services.inter.ISecurityService;

@Service
public class KeycloakSecurityService implements ISecurityService {
    @Override
    public JwtAuthenticationToken getAuthentication() {
        return (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getCurrentUserId() {
        Jwt jwt = getAuthentication().getToken();
        return jwt.getSubject();
    }

    @Override
    public String getCurrentUserEmail() {
        Jwt jwt = getAuthentication().getToken();
        return jwt.getClaimAsString("email");
    }
}
