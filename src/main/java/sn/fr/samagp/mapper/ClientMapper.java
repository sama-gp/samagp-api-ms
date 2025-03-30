package sn.fr.samagp.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "id", source = "id")
    Client toEntity(ClientDTO dto);
    ClientDTO toDto(Client client);
}
