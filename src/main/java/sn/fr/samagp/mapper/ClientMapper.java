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
    // Nouveaux champs
    @Mapping(target = "typePieces", source = "typePieces")
    @Mapping(target = "piecesRecto", source = "piecesRecto")
    @Mapping(target = "piecesVerso", source = "piecesVerso")
    @Mapping(target = "ninea", source = "ninea")
    Client toEntity(ClientDTO dto);

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "following", ignore = true)
    @Mapping(target = "profilePictureUrl", source = "profilePictureUrl")
    // Nouveaux champs
    @Mapping(target = "typePieces", source = "typePieces")
    @Mapping(target = "piecesRecto", source = "piecesRecto")
    @Mapping(target = "piecesVerso", source = "piecesVerso")
    @Mapping(target = "ninea", source = "ninea")
    ClientDTO toDto(Client client);

    default Set<UUID> mapClientsToIds(Set<Client> clients) {
        return clients == null ? Set.of() : clients.stream()
                .map(Client::getId)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "followingIds", source = "following", qualifiedByName = "clientsToIds")
    @Mapping(target = "followersIds", source = "followers", qualifiedByName = "clientsToIds")
    @Mapping(target = "annoncesCount", expression = "java(client.getAnnonces() != null ? client.getAnnonces().size() : 0)")
    @Mapping(target = "commentairesCount", expression = "java(client.getCommentaires() != null ? client.getCommentaires().size() : 0)")
    @Mapping(target = "profilePictureUrl", source = "profilePictureUrl")
    // Nouveaux champs pour la réponse
    @Mapping(target = "typePieces", source = "typePieces")
    @Mapping(target = "piecesRecto", source = "piecesRecto")
    @Mapping(target = "piecesVerso", source = "piecesVerso")
    @Mapping(target = "ninea", source = "ninea")
    ClientResponse toResponse(Client client);

    @Named("clientsToIds")
    default Set<UUID> clientsToIds(Set<Client> clients) {
        return clients == null ? Set.of() : clients.stream()
                .map(Client::getId)
                .collect(Collectors.toSet());
    }
}