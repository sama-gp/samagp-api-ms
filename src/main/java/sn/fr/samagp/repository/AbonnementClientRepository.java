package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.fr.samagp.repository.model.AbonnementClient;
import sn.fr.samagp.repository.model.StatutAbonnement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AbonnementClientRepository extends JpaRepository<AbonnementClient, UUID> {

    @Query("SELECT ac FROM AbonnementClient ac WHERE ac.client.id = :clientId AND ac.statut = 'ACTIF' AND ac.dateFin > CURRENT_TIMESTAMP")
    Optional<AbonnementClient> findAbonnementActifByClient(@Param("clientId") UUID clientId);

    List<AbonnementClient> findByClientIdAndStatut(UUID clientId, StatutAbonnement statut);

    List<AbonnementClient> findByClientIdOrderByDateDebutDesc(UUID clientId);

    List<AbonnementClient> findByStatutAndDateFinBefore(StatutAbonnement statut, LocalDateTime date);

    Long countByStatut(StatutAbonnement statut);
    List<AbonnementClient> findByClientId(UUID clientId);
}
