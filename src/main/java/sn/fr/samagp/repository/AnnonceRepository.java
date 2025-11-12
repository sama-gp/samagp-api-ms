package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.model.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnonceRepository extends JpaRepository<Annonce, UUID> , JpaSpecificationExecutor<Annonce> {
    List<Annonce> findByClientId(UUID clientId);
//    @Query("SELECT a FROM Annonce a WHERE a.id = :id")
//    Optional<Annonce> findByAltId(@Param("id") UUID id);
    List<Annonce> findByClientOrderByDateDepartDesc(Client client);

    List<Annonce> findByDateDepartBetween(LocalDateTime start, LocalDateTime end);


    // Méthode pour trouver les annonces par keycloakId du client
    @Query("SELECT a FROM Annonce a WHERE a.client.keycloakId = :keycloakId ORDER BY a.dateDepart DESC")
    List<Annonce> findByClientKeycloakId(@Param("keycloakId") String keycloakId);

    // Compter le nombre d'annonces par keycloakId
    @Query("SELECT COUNT(a) FROM Annonce a WHERE a.client.keycloakId = :keycloakId")
    Long countByClientKeycloakId(@Param("keycloakId") String keycloakId);

}
