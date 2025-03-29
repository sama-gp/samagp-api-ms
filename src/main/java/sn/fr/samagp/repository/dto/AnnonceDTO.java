package sn.fr.samagp.repository.dto;

import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnonceDTO {

    private ItineraireDTO itineraireDTO;
    private String itineraireDetailsDepart;
    private String itineraireDetailsArrive;
    private String description;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrive;
    private LocalDateTime updatedAt;
}