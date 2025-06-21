package sn.fr.samagp.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdAt", source = "createdAt")
    Client toEntity(ClientDTO dto);
    @Mapping(target = "createdAt", source = "createdAt")
    ClientDTO toDto(Client client);
}
