package sn.fr.samagp.services.impl;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sn.fr.samagp.exceptions.KeycloakServiceException;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Profile;
import sn.fr.samagp.services.inter.Ikeycloak;

import java.util.List;
import java.util.UUID;

@Service
public class KeycloakServiceImpl implements Ikeycloak {

    private final Keycloak keycloak;
    private final String realm;


    private  ClientRepository clientRepository;

    public KeycloakServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm,ClientRepository clientRepository) {
        this.keycloak = keycloak;
        this.realm = realm;
        this.clientRepository = clientRepository;
    }

    private RealmResource getRealmResource() {
        return keycloak.realm(realm);
    }

    private UsersResource getUsersResource() {
        return getRealmResource().users();
    }

    @Override
    public List<UserRepresentation> getAllUsers() {
        return getUsersResource().list();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public Response createUser(UserRepresentation user) {
        try {
            Response response = getUsersResource().create(user);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new KeycloakServiceException("Échec de la création de l'utilisateur. Statut: " + response.getStatus());
            }
            return response;
        } catch (Exception e) {
            throw new KeycloakServiceException("Erreur lors de la création de l'utilisateur", e);
        }
    }

    @Override
    public void updateUser(String userId, UserRepresentation user) {
        getUsersResource().get(userId).update(user);
    }

    @Override
    public void deleteUser(String userId) {
        getUsersResource().get(userId).remove();
    }

    @Override
    public List<UserRepresentation> searchUsers(String search) {
        return getUsersResource().search(search);
    }

    @Override
    public Client getOrCreateClientFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email;
        String firstName;
        String lastName;

        if (authentication == null) {
            throw new IllegalStateException("Aucune authentification trouvée dans le contexte.");
        }

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            var claims = jwtAuth.getToken().getClaims();
            email = (String) claims.get("email");
            firstName = (String) claims.get("given_name");
            lastName = (String) claims.get("family_name");

        } else if (authentication.getPrincipal() instanceof KeycloakPrincipal<?> keycloakPrincipal) {
            AccessToken token = keycloakPrincipal.getKeycloakSecurityContext().getToken();
            email = token.getEmail();
            firstName = token.getGivenName();
            lastName = token.getFamilyName();
        } else {
            lastName = null;
            firstName = null;
            email = null;
            throw new IllegalStateException("Utilisateur non connecté via Keycloak ou JWT.");
        }

        if (email == null) {
            throw new IllegalStateException("Email introuvable dans le token.");
        }

        return clientRepository.findByEmail(email)
                .orElseGet(() -> {
                    Client newClient = new Client();
                    newClient.setId(UUID.randomUUID());
                    newClient.setEmail(email);
                    newClient.setFirstName(firstName);
                    newClient.setLastName(lastName);
                    newClient.setProfile(Profile.GP);
                    return clientRepository.save(newClient);
                });
    }


    // @Override
    // public List<UserRepresentation> searchUsers(String username, String firstName, String lastName, String email) {
    //     return getUsersResource().search(username, firstName, lastName, email);
    // }

}
