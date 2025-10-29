package sn.fr.samagp.services.inter;
import sn.fr.samagp.controller.response.AbonnementResponseDTO;
import sn.fr.samagp.controller.response.AbonnementStatsDTO;
import sn.fr.samagp.repository.dto.*;

import java.util.List;
import java.util.UUID;

public interface IAbonnementService {

    // Plans d'abonnement
    List<PlanAbonnementDTO> getPlansActifs();
    PlanAbonnementDTO creerPlan(PlanAbonnementRequestDTO request);
    PlanAbonnementDTO mettreAJourPlan(UUID planId, PlanAbonnementRequestDTO request);

    // Souscription et gestion
    AbonnementResponseDTO souscrireAbonnement(UUID clientId, AbonnementClientDTO request);
    PaiementDTO traiterPaiement(PaiementRequestDTO request);
    AbonnementClientDTO getAbonnementActif(UUID clientId);
    boolean peutPublierAnnonce(UUID clientId);
    void incrementerAnnoncesUtilisees(UUID clientId);

    // Gestion administrative
    List<AbonnementClientDTO> getAbonnementsByClient(UUID clientId);
    List<AbonnementClientDTO> getAbonnementsExpirantSoon(int jours);
    void desactiverAbonnement(UUID abonnementId);

    // Statistiques
    AbonnementStatsDTO getStatistiques();
    List<PaiementDTO> getHistoriquePaiements(UUID clientId);
}