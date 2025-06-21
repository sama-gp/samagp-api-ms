package sn.fr.samagp.controller.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.fr.samagp.repository.dto.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnonceResponse {
    private UUID id;
    private ItineraireDTO itineraire;
    private String itineraireDetailsDepart;
    private String itineraireDetailsArrive;
    private String description;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<AvisDTO> avis;
    private List<CommentaireDto> commentaires;
    private ClientDTO client;
    private TarificationDTO tarification;
}
