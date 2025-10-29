package sn.fr.samagp.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.controller.response.AbonnementResponseDTO;
import sn.fr.samagp.controller.response.AbonnementStatsDTO;
import sn.fr.samagp.repository.dto.*;
import sn.fr.samagp.services.inter.IAbonnementService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/abonnements")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class AbonnementApi {

    private final IAbonnementService abonnementService;

    @GetMapping("/plans")
    public ResponseEntity<List<PlanAbonnementDTO>> getPlansActifs() {
        return ResponseEntity.ok(abonnementService.getPlansActifs());
    }

    @PostMapping("/plans")
    public ResponseEntity<PlanAbonnementDTO> creerPlan(@RequestBody PlanAbonnementRequestDTO request) {
        return ResponseEntity.ok(abonnementService.creerPlan(request));
    }

    @PutMapping("/plans/{planId}")
    public ResponseEntity<PlanAbonnementDTO> mettreAJourPlan(
            @PathVariable UUID planId,
            @RequestBody PlanAbonnementRequestDTO request) {
        return ResponseEntity.ok(abonnementService.mettreAJourPlan(planId, request));
    }

    @PostMapping("/souscrire/{clientId}")
    public ResponseEntity<AbonnementResponseDTO> souscrireAbonnement(
            @PathVariable UUID clientId,
            @RequestBody AbonnementClientDTO request) {
        return ResponseEntity.ok(abonnementService.souscrireAbonnement(clientId, request));
    }

    @PostMapping("/paiement")
    public ResponseEntity<PaiementDTO> traiterPaiement(@RequestBody PaiementRequestDTO request) {
        return ResponseEntity.ok(abonnementService.traiterPaiement(request));
    }

    @GetMapping("/client/{clientId}/actif")
    public ResponseEntity<AbonnementClientDTO> getAbonnementActif(@PathVariable UUID clientId) {
        AbonnementClientDTO abonnement = abonnementService.getAbonnementActif(clientId);
        return abonnement != null ? ResponseEntity.ok(abonnement) : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientId}/peut-publier")
    public ResponseEntity<Boolean> peutPublierAnnonce(@PathVariable UUID clientId) {
        return ResponseEntity.ok(abonnementService.peutPublierAnnonce(clientId));
    }

    @PostMapping("/client/{clientId}/incrementer-annonces")
    public ResponseEntity<Void> incrementerAnnoncesUtilisees(@PathVariable UUID clientId) {
        abonnementService.incrementerAnnoncesUtilisees(clientId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AbonnementClientDTO>> getAbonnementsByClient(@PathVariable UUID clientId) {
        List<AbonnementClientDTO> abonnements = abonnementService.getAbonnementsByClient(clientId);
        return ResponseEntity.ok(abonnements);
    }

    @GetMapping("/expirant-bientot")
    public ResponseEntity<List<AbonnementClientDTO>> getAbonnementsExpirantSoon(
            @RequestParam(defaultValue = "7") int jours) {
        return ResponseEntity.ok(abonnementService.getAbonnementsExpirantSoon(jours));
    }

    @DeleteMapping("/{abonnementId}")
    public ResponseEntity<Void> desactiverAbonnement(@PathVariable UUID abonnementId) {
        abonnementService.desactiverAbonnement(abonnementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistiques")
    public ResponseEntity<AbonnementStatsDTO> getStatistiques() {
        AbonnementStatsDTO stats = abonnementService.getStatistiques();
        return stats != null ? ResponseEntity.ok(stats) : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientId}/paiements")
    public ResponseEntity<List<PaiementDTO>> getHistoriquePaiements(@PathVariable UUID clientId) {
        List<PaiementDTO> paiements = abonnementService.getHistoriquePaiements(clientId);
        return ResponseEntity.ok(paiements);
    }
}