package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.fr.samagp.repository.model.PlanAbonnement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlanAbonnementRepository extends JpaRepository<PlanAbonnement, UUID> {
    List<PlanAbonnement> findByActifTrue();
    Optional<PlanAbonnement> findByCode(String code);
}