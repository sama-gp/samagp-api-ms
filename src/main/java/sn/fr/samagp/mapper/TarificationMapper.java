package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import sn.fr.samagp.repository.dto.TarificationDTO;
import sn.fr.samagp.repository.model.Tarification;

@Mapper(componentModel = "spring", uses = { FraisSupplementaireMapper.class })
public interface TarificationMapper {
    TarificationDTO toDto(Tarification entity);
    Tarification toEntity(TarificationDTO dto);
}
