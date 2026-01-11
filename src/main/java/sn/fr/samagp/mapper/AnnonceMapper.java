package sn.fr.samagp.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.dto.UpdateAnnonceDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.controller.response.AnnonceResponse;

@Mapper(componentModel = "spring", uses = { ClientMapper.class,ItineraireMapper.class,TarificationMapper.class })
public interface AnnonceMapper {
    AnnonceMapper INSTANCE = Mappers.getMapper(AnnonceMapper.class);

    @Mapping(target = "client", source = "clientDTO")
    @Mapping(target = "itineraireDetailsDepart", source = "itineraireDetailsDepart")
    @Mapping(target = "itineraireDetailsArrive", source = "itineraireDetailsArrive")
    Annonce toEntity(AnnonceDTO dto);

    @Mapping(target = "itineraireDetailsDepart", source = "itineraireDetailsDepart")
    @Mapping(target = "itineraireDetailsArrive", source = "itineraireDetailsArrive")
    Annonce toEntityUpdate(UpdateAnnonceDTO dto);

    @Mapping(target = "itineraireDetailsDepart", source = "itineraireDetailsDepart")
    @Mapping(target = "itineraireDetailsArrive", source = "itineraireDetailsArrive")
    AnnonceDTO toDto(Annonce annonce);
    @Mapping(target = "client", source = "client")
    AnnonceResponse toResponse(Annonce annonce);

    // Méthode pour convertir AnnonceDTO en UpdateAnnonceDTO
    UpdateAnnonceDTO toUpdateDto(AnnonceDTO annonceDTO);

    // Méthode pour mettre à jour une entité Annonce existante
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "tarification", source = "tarificationDTO")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnnonceFromDto(UpdateAnnonceDTO dto, @MappingTarget Annonce entity);

    // Méthode alternative pour la mise à jour avec AnnonceDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnnonceFromAnnonceDto(AnnonceDTO dto, @MappingTarget Annonce entity);

}