package sn.fr.samagp.repository.dto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UpdateAnnonceDTO(
        ItineraireDTO itineraireDTO,
        String itineraireDetailsDepart,
        String itineraireDetailsArrive,
        String description,
        LocalDateTime dateDepart,
        LocalDateTime dateArrive,
        LocalDateTime updatedAt,
        TarificationDTO tarificationDTO
) {

}