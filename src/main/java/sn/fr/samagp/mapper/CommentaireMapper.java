package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import sn.fr.samagp.controller.response.CommentaireResponse;
import sn.fr.samagp.repository.dto.CommentaireDto;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Commentaire;


@Mapper(componentModel = "spring",uses = {ClientMapper.class, AnnonceMapper.class})
public interface CommentaireMapper {
    @Mapping(target = "auteurId", source = "auteur.id")
    @Mapping(target = "auteurNom", source = "auteur", qualifiedByName = "getFullName")
    @Mapping(target = "annonceId", source = "annonce.id")
    CommentaireDto toDto(Commentaire commentaire);

    @Mapping(target = "auteur", ignore = true)
    @Mapping(target = "annonce", ignore = true)
    Commentaire toEntity(CommentaireDto commentaireDto);

    @Mapping(target = "auteurId", source = "auteur.id")
    @Mapping(target = "keycloakId", source = "auteur.keycloakId")
    @Mapping(target = "auteurNom", source = "auteur.lastName")
    @Mapping(target = "auteurPrenom", source = "auteur.firstName")
    @Mapping(target = "auteurEmail", source = "auteur.email")
    @Mapping(target = "annonceId", source = "annonce.id")
    CommentaireResponse toResponse(Commentaire commentaire);

    @Named("getFullName")
    default String getFullName(Client client) {
        return client.getFirstName() + " " + client.getLastName();
    }
}