package sn.fr.samagp.controller.response;

import java.math.BigDecimal;

public record PlanStatsDTO(
        String planNom,
        Long totalSouscriptions,
        BigDecimal revenusTotal
) {}