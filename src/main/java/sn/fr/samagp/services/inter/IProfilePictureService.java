package sn.fr.samagp.services.inter;

import org.springframework.web.multipart.MultipartFile;

public interface IProfilePictureService {

    /**
     * Met à jour la photo de profil d'un client
     * @param file le fichier image à uploader
     * @param keycloakId l'identifiant Keycloak du client
     * @return le chemin du fichier stocké
     */
    String updateProfilePicture(MultipartFile file, String keycloakId);

    /**
     * Supprime la photo de profil d'un client
     * @param keycloakId l'identifiant Keycloak du client
     */
    void deleteProfilePicture(String keycloakId);

    /**
     * Récupère l'URL de la photo de profil d'un client
     * @param keycloakId l'identifiant Keycloak du client
     * @return l'URL de la photo de profil
     */
    String getProfilePictureUrl(String keycloakId);
}