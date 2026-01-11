package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import sn.fr.samagp.repository.dto.FraisSupplementaireDTO;
import sn.fr.samagp.repository.model.FraisSupplementaire;

@Mapper(componentModel = "spring")
public interface FraisSupplementaireMapper {
    FraisSupplementaireDTO toDto(FraisSupplementaire entity);
    FraisSupplementaire toEntity(FraisSupplementaireDTO dto);
}
