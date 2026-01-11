package sn.fr.samagp.repository.dto;

import sn.fr.samagp.repository.model.StatutAbonnement;

import java.time.LocalDateTime;
import java.util.UUID;

public record AbonnementClientDTO(
        UUID id,
        UUID clientId,
        String clientNom,
        String clientEmail,
        PlanAbonnementDTO plan,
        LocalDateTime dateDebut,
        LocalDateTime dateFin,
        StatutAbonnement statut,
        Integer annoncesUtilisees,
        Boolean renouvellementAuto,
        LocalDateTime createdAt,
        boolean actif,
        boolean peutPublier
) {}