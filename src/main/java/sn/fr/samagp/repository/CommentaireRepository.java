package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.fr.samagp.repository.model.Commentaire;

import java.util.List;
import java.util.UUID;

public interface CommentaireRepository extends JpaRepository<Commentaire, UUID> {
    List<Commentaire> findByAnnonceIdOrderByDateCreationDesc(UUID annonceId);
    List<Commentaire> findByAuteurId(UUID clientId);
}