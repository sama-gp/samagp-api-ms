package sn.fr.samagp.controller.response;


import java.time.LocalDateTime;
import java.util.UUID;

public record CommentaireResponse(
        UUID id,
        String contenu,
        UUID auteurId,
        String keycloakId,
        String auteurNom,
        String auteurPrenom,
        String auteurEmail,
        String auteurPhoto,
        LocalDateTime dateCreation,
        UUID annonceId
) {
}