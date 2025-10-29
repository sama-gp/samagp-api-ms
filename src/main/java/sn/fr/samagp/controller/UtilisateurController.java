package sn.fr.samagp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.core.Response;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.fr.samagp.services.inter.Ikeycloak;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Keycloak")
@Tag(name = "Gestion des Utilisateurs", description = "API pour la gestion des utilisateurs via Keycloak")
public class UtilisateurController {

    private final Ikeycloak keycloakService;

    public UtilisateurController(Ikeycloak keycloakService) {
        this.keycloakService = keycloakService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Liste tous les utilisateurs",
            description = "Nécessite le rôle ADMIN")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès")
    @ApiResponse(responseCode = "403", description = "Accès non autorisé")
    public ResponseEntity<List<UserRepresentation>> getAllUsers() {
        return ResponseEntity.ok(keycloakService.getAllUsers());
    }

    @GetMapping("/{userId}")
    //@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.subject")
    @Operation(summary = "Récupère un utilisateur par son ID")
    public ResponseEntity<UserRepresentation> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok(keycloakService.getUserById(userId));
    }

    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crée un nouvel utilisateur")
    public ResponseEntity<Void> createUser(@RequestBody UserRepresentation user) {
        Response response = keycloakService.createUser(user);

        if (response.getStatus() == Response.Status.CREATED.getStatusCode()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{userId}")
                    .buildAndExpand(response.getLocation().getPath().split("/")[response.getLocation().getPath().split("/").length - 1])
                    .toUri();

            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{userId}")
    //@PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.subject")
    @Operation(summary = "Met à jour un utilisateur")
    public ResponseEntity<Void> updateUser(
            @PathVariable String userId,
            @RequestBody UserRepresentation user) {
        keycloakService.updateUser(userId, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprime un utilisateur")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        keycloakService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    //@PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Recherche des utilisateurs")
    public ResponseEntity<List<UserRepresentation>> searchUsers(
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(keycloakService.searchUsers(search));
    }

    // Si vous activez la méthode avancée de recherche plus tard
    /*
    @GetMapping("/advanced-search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserRepresentation>> advancedSearchUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email) {
        return ResponseEntity.ok(keycloakService.searchUsers(username, firstName, lastName, email));
    }
    */
}