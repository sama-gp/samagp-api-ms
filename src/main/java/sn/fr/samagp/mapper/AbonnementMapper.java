package sn.fr.samagp.mapper;

import org.mapstruct.*;
import sn.fr.samagp.repository.dto.*;
import sn.fr.samagp.repository.model.*;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AbonnementMapper {

    // PlanAbonnement Mappings
    PlanAbonnementDTO toDto(PlanAbonnement plan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PlanAbonnement toEntity(PlanAbonnementDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PlanAbonnement toEntity(PlanAbonnementRequestDTO requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePlanFromDto(PlanAbonnementRequestDTO dto, @MappingTarget PlanAbonnement plan);

    // AbonnementClient Mappings
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.fullName", target = "clientNom")
    @Mapping(source = "client.email", target = "clientEmail")
    @Mapping(target = "actif", expression = "java(abonnement.estActif())")
    @Mapping(target = "peutPublier", expression = "java(abonnement.peutPublierAnnonce())")
    AbonnementClientDTO toDto(AbonnementClient abonnement);

    List<AbonnementClientDTO> toAbonnementClientDtos(List<AbonnementClient> abonnements);

    // Paiement Mappings
    @Mapping(source = "abonnementClient.id", target = "abonnementClientId")
    PaiementDTO toDto(Paiement paiement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Paiement toEntity(PaiementDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reference", ignore = true)
    @Mapping(target = "devise", ignore = true)
    @Mapping(target = "statut", ignore = true)
    @Mapping(target = "datePaiement", ignore = true)
    @Mapping(target = "dateConfirmation", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Paiement toEntity(PaiementRequestDTO requestDto);

    List<PaiementDTO> toPaiementDtos(List<Paiement> paiements);
    List<PlanAbonnementDTO> toPlanDtos(List<PlanAbonnement> plans);

}