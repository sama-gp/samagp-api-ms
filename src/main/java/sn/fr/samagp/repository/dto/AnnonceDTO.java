package sn.fr.samagp.repository.dto;

import java.time.LocalDateTime;


public record AnnonceDTO(
        ItineraireDTO itineraireDTO,
        String itineraireDetailsDepart,
        String itineraireDetailsArrive,
        String description,
        LocalDateTime dateDepart,
        LocalDateTime dateArrive,
        LocalDateTime updatedAt
) {
}
