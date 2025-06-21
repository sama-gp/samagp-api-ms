package sn.fr.samagp.services.inter;

import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Client;

import java.util.UUID;

public interface IClientService {

    Client syncClient(ClientDTO dto);
    ClientDTO updateClient(String keycloakId, ClientDTO dto);
    Client addAddressToClient(UUID clientId, Adresse address);
    Client addPhoneToClient(UUID clientId, String phone);
    ClientDTO findByKeycloakId(String keycloakId);

}
