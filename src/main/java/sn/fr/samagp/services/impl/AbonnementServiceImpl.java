package sn.fr.samagp.services.impl;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.fr.samagp.controller.response.AbonnementResponseDTO;
import sn.fr.samagp.controller.response.AbonnementStatsDTO;
import sn.fr.samagp.mapper.AbonnementMapper;
import sn.fr.samagp.repository.*;
import sn.fr.samagp.repository.dto.*;
import sn.fr.samagp.repository.model.*;
import sn.fr.samagp.services.inter.IAbonnementService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AbonnementServiceImpl implements IAbonnementService {


    private final AbonnementClientRepository abonnementClientRepository;
    private final PlanAbonnementRepository planAbonnementRepository;
    private final PaiementRepository paiementRepository;
    private final HistoriqueAbonnementRepository historiqueAbonnementRepository;
    private final ClientRepository clientRepository;
    private final AbonnementMapper abonnementMapper;

    @Override
    public List<PlanAbonnementDTO> getPlansActifs() {
        log.info("Récupération des plans d'abonnement actifs");
        List<PlanAbonnement> plans = planAbonnementRepository.findByActifTrue();
        return abonnementMapper.toPlanDtos(plans);
    }

    @Override
    @Transactional
    public PlanAbonnementDTO creerPlan(PlanAbonnementRequestDTO request) {
        log.info("Création d'un nouveau plan d'abonnement: {}", request.code());

        // Vérifier l'unicité du code
        if (planAbonnementRepository.findByCode(request.code()).isPresent()) {
            throw new RuntimeException("Un plan avec le code " + request.code() + " existe déjà");
        }

        PlanAbonnement plan = abonnementMapper.toEntity(request);
        plan.setActif(true);

        PlanAbonnement savedPlan = planAbonnementRepository.save(plan);
        log.info("Plan créé avec succès: {}", savedPlan.getId());

        return abonnementMapper.toDto(savedPlan);
    }

    @Override
    @Transactional
    public PlanAbonnementDTO mettreAJourPlan(UUID planId, PlanAbonnementRequestDTO request) {
        log.info("Mise à jour du plan d'abonnement: {}", planId);

        PlanAbonnement plan = planAbonnementRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan d'abonnement non trouvé: " + planId));

        abonnementMapper.updatePlanFromDto(request, plan);
        PlanAbonnement updatedPlan = planAbonnementRepository.save(plan);

        log.info("Plan mis à jour avec succès: {}", planId);
        return abonnementMapper.toDto(updatedPlan);
    }


    @Override
    @Transactional
    public AbonnementResponseDTO souscrireAbonnement(UUID clientId, AbonnementClientDTO request) {
        log.info("Souscription à un abonnement - Client: {}, Plan: {}", clientId, request.plan().id());

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé: " + clientId));

        PlanAbonnement plan = planAbonnementRepository.findById(request.plan().id())
                .orElseThrow(() -> new RuntimeException("Plan d'abonnement non trouvé: " + request.plan().id()));

        if (!plan.getActif()) {
            throw new RuntimeException("Le plan d'abonnement n'est pas actif");
        }

        // Désactiver les anciens abonnements
        desactiverAnciensAbonnements(clientId);

        // Créer le nouvel abonnement
        AbonnementClient abonnement = AbonnementClient.builder()
                .client(client)
                .plan(plan)
                .dateDebut(LocalDateTime.now())
                .dateFin(calculerDateFin(plan.getDuree()))
                .statut(StatutAbonnement.EN_ATTENTE_PAIEMENT)
                .annoncesUtilisees(0)
                .renouvellementAuto(true)
                .build();

        AbonnementClient savedAbonnement = abonnementClientRepository.save(abonnement);

        log.info("Abonnement créé avec succès: {}", savedAbonnement.getId());

        return new AbonnementResponseDTO(
                abonnementMapper.toDto(savedAbonnement),
                null,
                "Abonnement créé avec succès. En attente de paiement."
        );
    }

    @Override
    @Transactional
    public PaiementDTO traiterPaiement(PaiementRequestDTO request) {
        log.info("Traitement du paiement pour l'abonnement: {}", request.abonnementClientId());

        AbonnementClient abonnement = abonnementClientRepository.findById(request.abonnementClientId())
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé: " + request.abonnementClientId()));

        // Créer le paiement
        Paiement paiement = Paiement.builder()
                .abonnementClient(abonnement)
                .reference(genererReferencePaiement())
                .montant(request.montant())
                .devise(abonnement.getPlan().getDevise())
                .methode(request.methode())
                .statut(StatutPaiement.PAYE)
                .idTransactionFournisseur(request.idTransactionFournisseur())
                .datePaiement(LocalDateTime.now())
                .dateConfirmation(LocalDateTime.now())
                .build();

        Paiement savedPaiement = paiementRepository.save(paiement);

        // Activer l'abonnement
        abonnement.setStatut(StatutAbonnement.ACTIF);
        abonnementClientRepository.save(abonnement);

        // Enregistrer l'historique
        enregistrerHistorique(abonnement, "NOUVEAU_ABONNEMENT");

        log.info("Paiement traité avec succès: {}", savedPaiement.getId());

        return abonnementMapper.toDto(savedPaiement);
    }

    @Override
    public AbonnementClientDTO getAbonnementActif(UUID clientId) {
        log.debug("Récupération de l'abonnement actif pour le client: {}", clientId);

        Optional<AbonnementClient> abonnement =
                abonnementClientRepository.findAbonnementActifByClient(clientId);

        return abonnement.map(abonnementMapper::toDto)
                .orElse(null);
    }

    @Override
    public boolean peutPublierAnnonce(UUID clientId) {
        Optional<AbonnementClient> abonnement =
                abonnementClientRepository.findAbonnementActifByClient(clientId);

        return abonnement.map(AbonnementClient::peutPublierAnnonce)
                .orElse(false);
    }

    @Override
    @Transactional
    public void incrementerAnnoncesUtilisees(UUID clientId) {
        abonnementClientRepository.findAbonnementActifByClient(clientId)
                .ifPresent(abonnement -> {
                    abonnement.incrementerAnnoncesUtilisees();
                    abonnementClientRepository.save(abonnement);
                    log.debug("Annonce utilisée incrémentée pour le client: {}", clientId);
                });
    }

    @Override
    public List<AbonnementClientDTO> getAbonnementsByClient(UUID clientId) {
        return null;
    }

    @Override
    public List<AbonnementClientDTO> getAbonnementsExpirantSoon(int jours) {
        log.debug("Récupération des abonnements expirant dans {} jours", jours);

        LocalDateTime dateLimite = LocalDateTime.now().plusDays(jours);
        List<AbonnementClient> abonnements =
                abonnementClientRepository.findByStatutAndDateFinBefore(
                        StatutAbonnement.ACTIF, dateLimite);

        return abonnementMapper.toAbonnementClientDtos(abonnements);
    }

    @Override
    @Transactional
    public void desactiverAbonnement(UUID abonnementId) {
        log.info("Désactivation de l'abonnement: {}", abonnementId);

        AbonnementClient abonnement = abonnementClientRepository.findById(abonnementId)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé: " + abonnementId));

        abonnement.setStatut(StatutAbonnement.ANNULE);
        abonnementClientRepository.save(abonnement);

        enregistrerHistorique(abonnement, "DESACTIVATION_MANUEL");
        log.info("Abonnement désactivé avec succès: {}", abonnementId);
    }

    @Override
    public AbonnementStatsDTO getStatistiques() {
        return null;
    }

    @Override
    public List<PaiementDTO> getHistoriquePaiements(UUID clientId) {
        return null;
    }

    // Méthodes privées utilitaires
    private LocalDateTime calculerDateFin(DureeAbonnement duree) {
        return switch (duree) {
            case MENSUEL -> LocalDateTime.now().plusMonths(1);
            case SEMESTRIEL -> LocalDateTime.now().plusMonths(6);
            case ANNUEL -> LocalDateTime.now().plusYears(1);
        };
    }

    private void desactiverAnciensAbonnements(UUID clientId) {
        abonnementClientRepository.findByClientIdAndStatut(clientId, StatutAbonnement.ACTIF)
                .forEach(abonnement -> {
                    abonnement.setStatut(StatutAbonnement.EXPIRE);
                    enregistrerHistorique(abonnement, "REMPLACE_PAR_NOUVEL_ABONNEMENT");
                });
    }

    private void enregistrerHistorique(AbonnementClient abonnement, String raison) {
        HistoriqueAbonnement historique = HistoriqueAbonnement.builder()
                .client(abonnement.getClient())
                .plan(abonnement.getPlan())
                .dateDebut(abonnement.getDateDebut())
                .dateFin(abonnement.getDateFin())
                .statut(abonnement.getStatut())
                .montantPaye(abonnement.getPlan().getPrix())
                .methodePaiement(MethodePaiement.CARTE_BANCAIRE)
                .raisonChangement(raison)
                .build();

        historiqueAbonnementRepository.save(historique);
    }

    private String genererReferencePaiement() {
        return "PAY-" + System.currentTimeMillis() + "-" +
                UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}