package sn.fr.samagp.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Profile;
import sn.fr.samagp.repository.model.TypePieces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Profile profile,
        List<String> phone,
        List<Adresse> address,
        String keycloakId,
        LocalDateTime createdAt,
        Set<UUID> followingIds,
        Set<UUID> followersIds,
        Integer annoncesCount,
        Integer commentairesCount,
        String profilePictureUrl,
        // Nouveaux champs
        TypePieces typePieces,
        String piecesRecto,
        String piecesVerso,
        String ninea,
        boolean isValid

) {

}