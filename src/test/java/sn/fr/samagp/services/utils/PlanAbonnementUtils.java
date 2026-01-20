package sn.fr.samagp.services.utils;

import sn.fr.samagp.repository.dto.PlanAbonnementDTO;
import sn.fr.samagp.repository.model.Devise;
import sn.fr.samagp.repository.model.DureeAbonnement;
import sn.fr.samagp.repository.model.PlanAbonnement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PlanAbonnementUtils {

    public static PlanAbonnement createPlanAbonnement(UUID id, String code, String nom,
                                                DureeAbonnement duree, BigDecimal prix,
                                                Integer nombreAnnonces) {
        PlanAbonnement plan = new PlanAbonnement();
        plan.setId(id);
        plan.setCode(code);
        plan.setNom(nom);
        plan.setDescription("Description pour " + nom);
        plan.setDuree(duree);
        plan.setPrix(prix);
        plan.setDevise(Devise.EUR);
        plan.setNombreAnnoncesInclus(nombreAnnonces);
        plan.setActif(true);
        plan.setCreatedAt(LocalDateTime.now());
        plan.setUpdatedAt(LocalDateTime.now());
        return plan;
    }

    public static PlanAbonnementDTO createPlanAbonnementDTO(UUID id, String code, String nom,
                                                      DureeAbonnement duree, BigDecimal prix,
                                                      Integer nombreAnnonces) {
        return new PlanAbonnementDTO(
                id,
                code,
                nom,
                "Description pour " + nom,
                duree,
                prix,
                Devise.EUR,
                nombreAnnonces,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
