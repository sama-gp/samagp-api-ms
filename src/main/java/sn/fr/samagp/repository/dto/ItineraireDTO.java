package sn.fr.samagp.repository.dto;

import lombok.Builder;

@Builder
public  record ItineraireDTO(
    ItinerraireIdDTO id,
    ZoneGeoDTO depart,
    ZoneGeoDTO arrivee
){}
