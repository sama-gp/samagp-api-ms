package sn.fr.samagp.services.inter;

import sn.fr.samagp.controller.response.ClientResponse;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.dto.FollowDTO;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Client;

import java.util.List;
import java.util.UUID;

public interface IClientService {

    Client syncClient(ClientDTO dto);
    ClientDTO updateClient(String keycloakId, ClientDTO dto);
    Client addAddressToClient(UUID clientId, Adresse address);
    Client addPhoneToClient(UUID clientId, String phone);
    ClientDTO findByKeycloakId(String keycloakId);

    FollowDTO followClient(String clientId);
    FollowDTO unfollowClient(String clientId);
    boolean isFollowing(String clientId);
    int getFollowersCount(String clientId);

    // Nouvelle méthode
    List<ClientResponse> getRecentClients(int limit);
    List<ClientResponse> getAllClients();

}
