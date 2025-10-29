package sn.fr.samagp.repository.dto;

import sn.fr.samagp.repository.model.Devise;
import sn.fr.samagp.repository.model.DureeAbonnement;

import java.math.BigDecimal;

public record PlanAbonnementRequestDTO(
        String code,
        String nom,
        String description,
        DureeAbonnement duree,
        BigDecimal prix,
        Devise devise,
        Integer nombreAnnoncesInclus,
        Boolean actif
) {}