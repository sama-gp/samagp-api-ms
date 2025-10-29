package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.fr.samagp.repository.model.HistoriqueAbonnement;

import java.util.List;
import java.util.UUID;

public interface HistoriqueAbonnementRepository extends JpaRepository<HistoriqueAbonnement, UUID> {
    List<HistoriqueAbonnement> findByClientIdOrderByCreatedAtDesc(UUID clientId);
}