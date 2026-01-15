package sn.fr.samagp.controller.request;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import sn.fr.samagp.repository.dto.ZoneGeoDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record AnnonceSearchCriteria (
        UUID itineraireId,
        ZoneGeoDTO depart,
        ZoneGeoDTO arrivee,
        @DateTimeFormat (pattern = "ddMMyyyy")
        LocalDateTime dateDepart,
        @DateTimeFormat (pattern = "ddMMyyyy")
        LocalDateTime dateArrivee,
        Boolean includeParentZones){
}
