package sn.fr.samagp.controller;


<<<<<<< HEAD:src/main/java/sn/fr/samagp/controller/AnnonceController.java
import lombok.RequiredArgsConstructor;
=======
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
>>>>>>> d1acccb (feat(keycloak): Integration de keycloak):src/main/java/sn/fr/samagp/application/AnnonceController.java
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.controller.request.AnnonceSearchCriteria;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.controller.response.AnnonceResponse;
import sn.fr.samagp.services.inter.IAnnonce;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/annonces")
<<<<<<< HEAD:src/main/java/sn/fr/samagp/controller/AnnonceController.java
@RequiredArgsConstructor
=======
@SecurityRequirement(name = "Keycloak")
>>>>>>> d1acccb (feat(keycloak): Integration de keycloak):src/main/java/sn/fr/samagp/application/AnnonceController.java
public class AnnonceController {

    private final IAnnonce annonceService;


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
    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> filter (@ModelAttribute AnnonceSearchCriteria criteria){
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
    @PostMapping
    public ResponseEntity<AnnonceResponse> createAnnonce(@RequestBody AnnonceDTO annonceDTO) {
        AnnonceResponse createdAnnonce = annonceService.createAnnonce(annonceDTO);
        return ResponseEntity.status(201).body(createdAnnonce);
    }

    // Mettre à jour une annonce
    @PutMapping("/{id}")
    public ResponseEntity<AnnonceDTO> updateAnnonce(@PathVariable UUID id, @RequestBody AnnonceDTO annonceDTO) {
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

}
