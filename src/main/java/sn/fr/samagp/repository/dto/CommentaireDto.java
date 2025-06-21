package sn.fr.samagp.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireDto {
    private UUID id;
    private String contenu;
    private String auteurId;
    private String auteurNom;
    private LocalDateTime dateCreation;
    private UUID annonceId;
}