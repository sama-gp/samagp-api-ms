package sn.fr.samagp.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.controller.response.AbonnementResponseDTO;
import sn.fr.samagp.controller.response.AbonnementStatsDTO;
import sn.fr.samagp.controller.response.StripePaymentResponse;
import sn.fr.samagp.repository.dto.*;
import sn.fr.samagp.services.inter.IAbonnementService;
import sn.fr.samagp.services.inter.IFactureService;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/abonnements")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class AbonnementApi {

    private final IAbonnementService abonnementService;
    private final IFactureService factureService;

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

    @PostMapping("/souscrire/{clientKeycloakId}")
    public ResponseEntity<AbonnementResponseDTO> souscrireAbonnement(
            @PathVariable String clientKeycloakId,
            @RequestBody AbonnementClientDTO request) {
        return ResponseEntity.ok(abonnementService.souscrireAbonnement(clientKeycloakId, request));
    }

    @PostMapping("/paiement")
    public ResponseEntity<PaiementDTO> traiterPaiement(@RequestBody PaiementRequestDTO request) {
        return ResponseEntity.ok(abonnementService.traiterPaiement(request));
    }

    @GetMapping("/client/{clientKeycloakId}/actif")
    public ResponseEntity<AbonnementClientDTO> getAbonnementActif(@PathVariable String clientKeycloakId) {
        AbonnementClientDTO abonnement = abonnementService.getAbonnementActif(clientKeycloakId);
        return abonnement != null ? ResponseEntity.ok(abonnement) : ResponseEntity.notFound().build();
    }

    @GetMapping("/client/{clientKeycloakId}/peut-publier")
    public ResponseEntity<Boolean> peutPublierAnnonce(@PathVariable String clientKeycloakId) {
        return ResponseEntity.ok(abonnementService.peutPublierAnnonce(clientKeycloakId));
    }

    @PostMapping("/client/{clientKeycloakId}/incrementer-annonces")
    public ResponseEntity<Void> incrementerAnnoncesUtilisees(@PathVariable String clientKeycloakId) {
        abonnementService.incrementerAnnoncesUtilisees(clientKeycloakId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientKeycloakId}")
    public ResponseEntity<List<AbonnementClientDTO>> getAbonnementsByClient(@PathVariable String clientKeycloakId) {
        List<AbonnementClientDTO> abonnements = abonnementService.getAbonnementsByClient(clientKeycloakId);
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

    @GetMapping("/client/{clientKeycloakId}/paiements")
    public ResponseEntity<List<PaiementDTO>> getHistoriquePaiements(@PathVariable String clientKeycloakId) {
        List<PaiementDTO> paiements = abonnementService.getHistoriquePaiements(clientKeycloakId);
        return ResponseEntity.ok(paiements);
    }

    @PostMapping("/stripe/payment/{clientKeycloakId}")
    public ResponseEntity<StripePaymentResponse> processStripePayment(
            @PathVariable String clientKeycloakId,
            @RequestBody StripePaymentRequest request) {
        StripePaymentResponse response = abonnementService.processStripePayment(
                clientKeycloakId, request.abonnementClientId(), request.stripeToken(), request.email());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stripe/confirm")
    public ResponseEntity<StripePaymentResponse> confirmStripePayment(
            @RequestParam String paymentIntentId) {
        StripePaymentResponse response = abonnementService.confirmStripePayment(paymentIntentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            // Implémentez la vérification de la signature webhook ici
            // et traitez les événements Stripe
            return ResponseEntity.ok("Webhook processed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook error: " + e.getMessage());
        }
    }

    @GetMapping("/paiements/{paiementId}/facture")
    public ResponseEntity<InputStreamResource> downloadFacture(@PathVariable UUID paiementId) {
        try {
            ByteArrayInputStream facturePdf = factureService.genererFacturePdf(paiementId);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=facture-" + paiementId + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(facturePdf));

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de la facture: " + e.getMessage());
        }
    }

}