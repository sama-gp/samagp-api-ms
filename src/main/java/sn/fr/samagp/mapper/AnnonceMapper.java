package sn.fr.samagp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.response.AnnonceResponse;

@Mapper(componentModel = "spring", uses = { ClientMapper.class})
public interface AnnonceMapper {
    AnnonceMapper INSTANCE = Mappers.getMapper(AnnonceMapper.class);

    @Mapping(target = "client", source = "clientDTO")
    Annonce toEntity(AnnonceDTO dto);
    AnnonceDTO toDto(Annonce annonce);
    @Mapping(target = "client", source = "client")
    AnnonceResponse toResponse(Annonce annonce);
}