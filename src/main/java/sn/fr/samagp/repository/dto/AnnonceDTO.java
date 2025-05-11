package sn.fr.samagp.repository.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
public record AnnonceDTO( ItineraireDTO itineraireDTO,
         String itineraireDetailsDepart,
         String itineraireDetailsArrive,
         String description,
         LocalDateTime dateDepart,
         LocalDateTime dateArrive,
         LocalDateTime updatedAt,
         ClientDTO clientDTO) {


}