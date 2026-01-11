package sn.fr.samagp.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Profile;
import sn.fr.samagp.services.inter.IClientService;

import java.util.Map;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
public class KeycloakSyncController {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakSyncController.class);
    private final IClientService clientService;

    @PostMapping("/user")
    public ResponseEntity<String> syncUser(@RequestBody Map<String, Object> userData) {
        try {
            String eventType = (String) userData.get("eventType");
            String keycloakId = (String) userData.get("keycloakId");
            String email = (String) userData.get("email");
            String firstName = (String) userData.get("firstName");
            String lastName = (String) userData.get("lastName");

            logger.info("🔄 Synchronisation utilisateur - Type: {}, ID: {}, Email: {}",
                    eventType, keycloakId, email);

            // Créer le ClientDTO
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setKeycloakId(keycloakId);
            clientDTO.setEmail(email);
            clientDTO.setFirstName(firstName != null ? firstName : "");
            clientDTO.setLastName(lastName != null ? lastName : "");
            clientDTO.setProfile(Profile.CLIENT);

            Client createdClient;

            switch (eventType) {
                case "REGISTER":
                    createdClient = clientService.syncClient(clientDTO);
                    logger.info("✅ Utilisateur créé avec ID: {}", createdClient.getId());
                    break;

                case "UPDATE_PROFILE":
                    clientService.updateClient(keycloakId, clientDTO);
                    logger.info("✅ Profil mis à jour pour: {}", keycloakId);
                    break;

                case "UPDATE_EMAIL":
                    clientService.updateClient(keycloakId, clientDTO);
                    logger.info("✅ Email mis à jour pour: {}", keycloakId);
                    break;

                default:
                    logger.warn("⚠️ Type d'événement non géré: {}", eventType);
                    return ResponseEntity.badRequest().body("Type d'événement non supporté");
            }

            return ResponseEntity.ok("Synchronisation réussie");

        } catch (Exception e) {
            logger.error("❌ Erreur synchronisation utilisateur: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur synchronisation: " + e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<String> syncAuth(@RequestBody Map<String, Object> authData) {
        try {
            String eventType = (String) authData.get("eventType");
            String keycloakId = (String) authData.get("keycloakId");
            String email = (String) authData.get("email");
            String firstName = (String) authData.get("firstName");
            String lastName = (String) authData.get("lastName");

            logger.info("🔐 Synchronisation auth: {} pour l'utilisateur: {}", eventType, keycloakId);

            if ("LOGIN".equals(eventType)) {
                // Vérifier si l'utilisateur existe déjà
                try {
                    clientService.findByKeycloakId(keycloakId);
                    logger.debug("✅ Utilisateur {} existe déjà", keycloakId);
                } catch (Exception e) {
                    // Si l'utilisateur n'existe pas, le créer automatiquement
                    logger.info("👤 Création automatique à la première connexion: {}", keycloakId);

                    ClientDTO clientDTO = new ClientDTO();
                    clientDTO.setKeycloakId(keycloakId);
                    clientDTO.setEmail(email != null ? email : "");
                    clientDTO.setFirstName(firstName != null ? firstName : "");
                    clientDTO.setLastName(lastName != null ? lastName : "");
                    clientDTO.setProfile(Profile.CLIENT);

                    Client createdClient = clientService.syncClient(clientDTO);
                    logger.info("✅ Utilisateur créé automatiquement: {}", createdClient.getId());
                }
            }

            return ResponseEntity.ok("Auth synchronisée");

        } catch (Exception e) {
            logger.error("❌ Erreur synchronisation auth: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur synchronisation auth: " + e.getMessage());
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId,
                                             @RequestBody Map<String, Object> updateData) {
        try {
            logger.info("📝 Mise à jour utilisateur: {}", userId);

            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setFirstName((String) updateData.get("firstName"));
            clientDTO.setLastName((String) updateData.get("lastName"));
            clientDTO.setEmail((String) updateData.get("email"));

            clientService.updateClient(userId, clientDTO);
            logger.info("✅ Utilisateur mis à jour: {}", userId);

            return ResponseEntity.ok("Mise à jour réussie");

        } catch (Exception e) {
            logger.error("❌ Erreur mise à jour utilisateur {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(500).body("Erreur mise à jour: " + e.getMessage());
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            logger.info("🗑️ Suppression utilisateur: {}", userId);
            // Implémenter la suppression si nécessaire
            return ResponseEntity.ok("Suppression désactivée");
        } catch (Exception e) {
            logger.error("❌ Erreur suppression utilisateur {}: {}", userId, e.getMessage());
            return ResponseEntity.status(500).body("Erreur suppression");
        }
    }
}