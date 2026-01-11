package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.fr.samagp.repository.model.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByKeycloakId(String keycloakId);

    @Query("SELECT c FROM Client c WHERE c.isValid = false AND " +
            "c.typePieces IS NOT NULL AND " +
            "c.piecesRecto IS NOT NULL AND " +
            "(c.typePieces != sn.fr.samagp.repository.model.TypePieces.CARTE_NATIONALE OR c.piecesVerso IS NOT NULL) AND " +
            "(c.profile != sn.fr.samagp.repository.model.Profile.GP OR c.ninea IS NOT NULL)")
    List<Client> findByIsValidFalseAndHasAllDocuments();

}
