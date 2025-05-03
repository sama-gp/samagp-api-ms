package sn.fr.samagp.services.impl;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import sn.fr.samagp.exceptions.KeycloakServiceException;
import sn.fr.samagp.services.inter.Ikeycloak;

import java.util.List;

public class KeycloakServiceImpl implements Ikeycloak {

    private final Keycloak keycloak;
    private final String realm;

    public KeycloakServiceImpl(Keycloak keycloak, @Value("${keycloak.realm}") String realm) {
        this.keycloak = keycloak;
        this.realm = realm;
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

    // @Override
    // public List<UserRepresentation> searchUsers(String username, String firstName, String lastName, String email) {
    //     return getUsersResource().search(username, firstName, lastName, email);
    // }

}
