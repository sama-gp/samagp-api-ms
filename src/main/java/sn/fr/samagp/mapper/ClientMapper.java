package sn.fr.samagp.mapper;


import org.mapstruct.Mapper;
import sn.fr.samagp.services.EntityRetrievalService;

@Mapper(componentModel = "spring", uses = {EntityRetrievalService.class})
public interface ClientMapper {
}
