package sn.fr.samagp.services.inter;
import sn.fr.samagp.controller.response.AbonnementResponseDTO;
import sn.fr.samagp.controller.response.AbonnementStatsDTO;
import sn.fr.samagp.controller.response.StripePaymentResponse;
import sn.fr.samagp.repository.dto.*;

import java.util.List;
import java.util.UUID;

public interface IAbonnementService {

    // Plans d'abonnement
    List<PlanAbonnementDTO> getPlansActifs();
    PlanAbonnementDTO creerPlan(PlanAbonnementRequestDTO request);
    PlanAbonnementDTO mettreAJourPlan(UUID planId, PlanAbonnementRequestDTO request);

    // Souscription et gestion - CHANGER ICI
    AbonnementResponseDTO souscrireAbonnement(String clientKeycloakId, AbonnementClientDTO request);
    PaiementDTO traiterPaiement(PaiementRequestDTO request);
    AbonnementClientDTO getAbonnementActif(String clientKeycloakId);
    boolean peutPublierAnnonce(String clientKeycloakId);
    void incrementerAnnoncesUtilisees(String clientKeycloakId);

    // Gestion administrative - CHANGER ICI
    List<AbonnementClientDTO> getAbonnementsByClient(String clientKeycloakId);
    List<AbonnementClientDTO> getAbonnementsExpirantSoon(int jours);
    void desactiverAbonnement(UUID abonnementId);

    // Statistiques - CHANGER ICI
    AbonnementStatsDTO getStatistiques();
    List<PaiementDTO> getHistoriquePaiements(String clientKeycloakId);

    StripePaymentResponse processStripePayment(String clientKeycloakId, UUID abonnementClientId, String stripeToken, String email);
    StripePaymentResponse confirmStripePayment(String paymentIntentId);
}
