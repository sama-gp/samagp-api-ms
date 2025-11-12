package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.fr.samagp.repository.model.Paiement;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaiementRepository extends JpaRepository<Paiement, UUID> {
    Optional<Paiement> findByReference(String reference);

    List<Paiement> findByAbonnementClientClientIdOrderByDatePaiementDesc(UUID clientId);

    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.datePaiement >= :debutMois AND p.statut = 'PAYE'")
    BigDecimal calculateRevenueMensuel(@Param("debutMois") LocalDateTime debutMois);

    List<Paiement> findByAbonnementClient_Client_Id(UUID clientId);


    default BigDecimal calculateRevenueMensuel() {
        LocalDateTime debutMois = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return calculateRevenueMensuel(debutMois);
    }

}