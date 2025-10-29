package sn.fr.samagp.controller;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.controller.request.AnnonceSearchCriteria;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.controller.response.AnnonceResponse;
import sn.fr.samagp.repository.dto.UpdateAnnonceDTO;
import sn.fr.samagp.repository.dto.ZoneGeoDTO;
import sn.fr.samagp.services.inter.IAnnonce;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/annonces")
@RequiredArgsConstructor
@SecurityRequirement(name = "Keycloak")
public class AnnonceController {

    private static final Logger log = LoggerFactory.getLogger(AnnonceController.class);
    private final IAnnonce annonceService;


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AnnonceResponse>> getAnnoncesByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(annonceService.getAnnoncesByUser(userId));
    }

    @GetMapping("/today")
    public ResponseEntity<List<AnnonceResponse>> getAnnoncesToday() {
        return ResponseEntity.ok(annonceService.getAnnoncesToday());
    }

    @GetMapping("/week")
    public ResponseEntity<List<AnnonceResponse>> getAnnoncesThisWeek() {
        return ResponseEntity.ok(annonceService.getAnnoncesThisWeek());
    }

    // Récupérer toutes les annonces
    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> getAllAnnonces() {
        List<AnnonceResponse> annonces = annonceService.getAllAnnonces();
        return ResponseEntity.ok(annonces);
    }

    /*TODO:
    * Gestion des exceptions
    * Validator for criteria
    */
    @GetMapping("/search")
    public ResponseEntity<List<AnnonceResponse>> filter(
            @RequestParam(required = false) UUID itineraireId,
            @RequestParam(required = false) Long departId,
            @RequestParam(required = false) String departLibelle,
            @RequestParam(required = false) Long arriveeId,
            @RequestParam(required = false) String arriveeLibelle,
            @RequestParam(required = false) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate dateDepart, // CHANGEMENT: pattern au lieu de iso
            @RequestParam(required = false) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate dateArrivee, // CHANGEMENT: pattern au lieu de iso
            @RequestParam(required = false) Boolean includeParentZones,
            @RequestParam(required = false) String description) {

        ZoneGeoDTO depart = null;
        if (departId != null || departLibelle != null) {
            depart = new ZoneGeoDTO(departId, departLibelle, null, null);
        }

        ZoneGeoDTO arrivee = null;
        if (arriveeId != null || arriveeLibelle != null) {
            arrivee = new ZoneGeoDTO(arriveeId, arriveeLibelle, null, null);
        }

        // Convertir LocalDate en LocalDateTime
        LocalDateTime dateTimeDepart = dateDepart != null ? dateDepart.atStartOfDay() : null;
        LocalDateTime dateTimeArrivee = dateArrivee != null ? dateArrivee.atStartOfDay() : null;

        AnnonceSearchCriteria criteria = AnnonceSearchCriteria.builder()
                .itineraireId(itineraireId)
                .depart(depart)
                .arrivee(arrivee)
                .dateDepart(dateTimeDepart)
                .dateArrivee(dateTimeArrivee)
                .includeParentZones(includeParentZones)
                .build();

        return ResponseEntity.ok(annonceService.filterByCriteria(criteria));
    }

    // Récupérer une annonce par son ID
    @GetMapping("/{id}")
    public ResponseEntity<AnnonceResponse> getAnnonceById(@PathVariable UUID id) {
        return annonceService.getAnnonceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Créer une nouvelle annonce
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnnonceResponse> createAnnonce(@RequestBody AnnonceDTO annonceDTO) {
        AnnonceResponse createdAnnonce = annonceService.createAnnonce(annonceDTO);
        return ResponseEntity.status(201).body(createdAnnonce);
    }

    // Mettre à jour une annonce
    @PutMapping(value ="/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnnonceDTO> updateAnnonce(@PathVariable UUID id, @RequestBody UpdateAnnonceDTO annonceDTO) {
        try {
            AnnonceDTO updatedAnnonce = annonceService.updateAnnonce(id, annonceDTO);
            return ResponseEntity.ok(updatedAnnonce);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une annonce
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable UUID id) {
         annonceService.deleteAnnonce(id);
        return ResponseEntity.noContent().build();
    }

    // Récupérer les annonces par client
    @GetMapping("/client/{idClient}")
    public ResponseEntity<List<AnnonceResponse>> getAnnonceByClient(@PathVariable UUID idClient) {
        List<AnnonceResponse> annonces = annonceService.getAnnonceByClient(idClient);
        return ResponseEntity.ok(annonces);
    }

    @GetMapping("/client/email/{email}")
    public ResponseEntity<List<AnnonceResponse>> getAnnoncesByUserEmail(@PathVariable String email) {
        log.debug("Fetching annonces for user with email: {}", email);
        try {
            List<AnnonceResponse> annonces = annonceService.getAnnoncesByUserEmail(email);
            return ResponseEntity.ok(annonces);
        } catch (EntityNotFoundException e) {
            log.warn("User not found with email: {}", email);
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Invalid email parameter: {}", email);
            return ResponseEntity.badRequest().build();
        }
    }

}
