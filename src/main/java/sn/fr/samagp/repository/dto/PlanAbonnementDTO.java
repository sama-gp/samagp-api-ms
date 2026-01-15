package sn.fr.samagp.repository.dto;

import sn.fr.samagp.repository.model.Devise;
import sn.fr.samagp.repository.model.DureeAbonnement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PlanAbonnementDTO(
        UUID id,
        String code,
        String nom,
        String description,
        DureeAbonnement duree,
        BigDecimal prix,
        Devise devise,
        Integer nombreAnnoncesInclus,
        Boolean actif,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
