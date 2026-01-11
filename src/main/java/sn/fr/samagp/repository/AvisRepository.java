package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.fr.samagp.repository.model.Avis;

import java.util.UUID;

@Repository
public interface AvisRepository  extends JpaRepository<Avis, UUID> {
}
