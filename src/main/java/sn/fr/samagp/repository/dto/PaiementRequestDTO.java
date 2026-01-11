package sn.fr.samagp.repository.dto;

import sn.fr.samagp.repository.model.MethodePaiement;

import java.math.BigDecimal;
import java.util.UUID;

public record PaiementRequestDTO(
        UUID abonnementClientId,
        BigDecimal montant,
        MethodePaiement methode,
        String idTransactionFournisseur,
        String detailsTransaction
) {}