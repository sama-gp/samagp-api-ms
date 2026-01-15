package sn.fr.samagp.services.inter;

import jakarta.ws.rs.core.Response;
import org.keycloak.representations.idm.UserRepresentation;
import sn.fr.samagp.repository.model.Client;

import java.util.List;

public interface Ikeycloak {

    List<UserRepresentation> getAllUsers();
    UserRepresentation getUserById(String userId);
    Response createUser(UserRepresentation user);
    void updateUser(String userId, UserRepresentation user);
    void deleteUser(String userId);
    List<UserRepresentation> searchUsers(String search);
    public Client getOrCreateClientFromToken();
}
