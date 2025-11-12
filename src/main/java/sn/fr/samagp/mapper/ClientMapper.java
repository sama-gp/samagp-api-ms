package sn.fr.samagp.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import org.mapstruct.Named;
import sn.fr.samagp.controller.response.ClientResponse;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Client;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "following", ignore = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "profilePictureUrl", source = "profilePictureUrl")
    Client toEntity(ClientDTO dto);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "following", ignore = true)
    @Mapping(target = "profilePictureUrl", source = "profilePictureUrl")
    ClientDTO toDto(Client client);



    default Set<UUID> mapClientsToIds(Set<Client> clients) {
        return clients == null ? Set.of() : clients.stream()
                .map(Client::getId)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "followingIds", source = "following")
    @Mapping(target = "followersIds", source = "followers")
    @Mapping(target = "profilePictureUrl", source = "profilePictureUrl")
    ClientResponse toResponse(Client client);
}
