package sn.fr.samagp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.controller.response.CommentaireResponse;
import sn.fr.samagp.repository.dto.CommentaireDto;
import sn.fr.samagp.services.inter.ICommentaireService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/commentaires")
@SecurityRequirement(name = "Keycloak")
@RequiredArgsConstructor
public class CommentaireController {

    private final ICommentaireService commentaireService;

    @PostMapping
    public ResponseEntity<CommentaireResponse> createCommentaire(@RequestBody CommentaireDto commentaireDto) {
        return ResponseEntity.ok(commentaireService.createCommentaire(commentaireDto));
    }

    @GetMapping("/annonce/{annonceId}")
    public ResponseEntity<List<CommentaireResponse>> getCommentairesByAnnonce(@PathVariable UUID annonceId) {
        return ResponseEntity.ok(commentaireService.getCommentairesByAnnonce(annonceId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable UUID id) {
        commentaireService.deleteCommentaire(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentaireResponse> updateCommentaire(
            @PathVariable UUID id,
            @RequestBody CommentaireDto commentaireDto) {
        CommentaireResponse response = commentaireService.updateCommentaire(id, commentaireDto);
        return ResponseEntity.ok(response);
    }
}