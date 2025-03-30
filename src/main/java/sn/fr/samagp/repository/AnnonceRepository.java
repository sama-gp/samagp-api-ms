package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sn.fr.samagp.repository.model.Annonce;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AnnonceRepository extends JpaRepository<Annonce, UUID> {
    List<Annonce> findByClientId(UUID clientId);
    @Query("SELECT a FROM Annonce a WHERE a.id = :id")
    Optional<Annonce> findByAltId(@Param("id") UUID id);

}
