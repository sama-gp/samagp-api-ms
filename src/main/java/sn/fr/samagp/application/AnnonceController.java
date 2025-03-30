package sn.fr.samagp.application;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.response.AnnonceResponse;
import sn.fr.samagp.services.inter.IAnnonce;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/annonces")
public class AnnonceController {

    private final IAnnonce annonceService;

    public AnnonceController(IAnnonce annonceService) {
        this.annonceService = annonceService;
    }

    // Récupérer toutes les annonces
    @GetMapping
    public ResponseEntity<List<AnnonceResponse>> getAllAnnonces() {
        List<AnnonceResponse> annonces = annonceService.getAllAnnonces();
        return ResponseEntity.ok(annonces);
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
