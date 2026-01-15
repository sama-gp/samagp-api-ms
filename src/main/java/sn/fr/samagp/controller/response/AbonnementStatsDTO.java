package sn.fr.samagp.controller.response;

import java.math.BigDecimal;
import java.util.List;

public record AbonnementStatsDTO(
        Long totalAbonnements,
        Long abonnementsActifs,
        Long abonnementsExpires,
        BigDecimal revenusMensuels,
        List<PlanStatsDTO> statsParPlan
) {}