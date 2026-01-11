package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.fr.samagp.repository.dto.ItineraireDTO;
import sn.fr.samagp.repository.dto.ItinerraireIdDTO;
import sn.fr.samagp.repository.model.Itinerraire;
import sn.fr.samagp.repository.model.ItinerraireId;

@Mapper(componentModel = "spring", uses = {ZoneGeoMapper.class})
public interface ItineraireMapper {
    @Mapping(target = "id", expression = "java(toItinerraireIdDTO(entity))")
    @Mapping(target = "depart", source = "depart")
    @Mapping(target = "arrivee", source = "arrivee")
    ItineraireDTO toDto(Itinerraire entity);

    @Mapping(target = "id", expression = "java(toItinerraireId(dto.id()))")
    @Mapping(target = "depart", source = "depart")
    @Mapping(target = "arrivee", source = "arrivee")
    Itinerraire toEntity(ItineraireDTO dto);

    default ItinerraireIdDTO toItinerraireIdDTO(Itinerraire entity) {
        return new ItinerraireIdDTO(
                entity.getDepart() != null ? entity.getDepart().getId() : null,
                entity.getArrivee() != null ? entity.getArrivee().getId() : null
        );
    }

    default ItinerraireId toItinerraireId(ItinerraireIdDTO idDto) {
        if (idDto == null) {
            return new ItinerraireId();
        }
        return new ItinerraireId(idDto.departId(), idDto.arriveeId());
    }
}
