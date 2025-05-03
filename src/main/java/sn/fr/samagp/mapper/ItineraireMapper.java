package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import sn.fr.samagp.repository.dto.ItineraireDTO;
import sn.fr.samagp.repository.model.Itinerraire;

@Mapper(componentModel = "spring")
public interface ItineraireMapper {
    ItineraireDTO toDto(Itinerraire entity);
    Itinerraire toEntity(ItineraireDTO dto);
}
