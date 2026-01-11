package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import sn.fr.samagp.repository.dto.ZoneGeoDTO;
import sn.fr.samagp.repository.model.ZoneGeo;

@Mapper(componentModel = "spring")
public interface ZoneGeoMapper {
    ZoneGeoDTO toDto(ZoneGeo entity);
    ZoneGeo toEntity(ZoneGeoDTO dto);
}