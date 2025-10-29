package sn.fr.samagp.controller.response;

import sn.fr.samagp.repository.dto.AbonnementClientDTO;
import sn.fr.samagp.repository.dto.PaiementDTO;

public record AbonnementResponseDTO(
        AbonnementClientDTO abonnement,
        PaiementDTO paiement,
        String message
) {}