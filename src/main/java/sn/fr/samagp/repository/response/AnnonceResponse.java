package sn.fr.samagp.repository.response;

import sn.fr.samagp.repository.dto.AvisDTO;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.dto.ItineraireDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record AnnonceResponse(
        UUID id,
        ItineraireDTO itineraire,
        String itineraireDetailsDepart,
        String itineraireDetailsArrive,
        String description,
        LocalDateTime dateDepart,
        LocalDateTime dateArrive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<AvisDTO> avis,
        ClientDTO client
) {
}
