package sn.fr.samagp.repository.dto;

import sn.fr.samagp.repository.model.Devise;
import sn.fr.samagp.repository.model.MethodePaiement;
import sn.fr.samagp.repository.model.StatutPaiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaiementDTO(
        UUID id,
        UUID abonnementClientId,
        String reference,
        BigDecimal montant,
        Devise devise,
        MethodePaiement methode,
        StatutPaiement statut,
        String idTransactionFournisseur,
        LocalDateTime datePaiement,
        LocalDateTime dateConfirmation,
        LocalDateTime createdAt
) {}