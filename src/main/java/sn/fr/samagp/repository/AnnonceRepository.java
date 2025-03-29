package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.fr.samagp.repository.model.Annonce;

import java.util.List;
import java.util.UUID;

public interface AnnonceRepository extends JpaRepository<Annonce, UUID> {
    List<Annonce> findByClientId(UUID clientId);
}
