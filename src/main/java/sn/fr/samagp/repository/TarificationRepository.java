package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.fr.samagp.repository.model.Tarification;

import java.util.UUID;

public interface TarificationRepository extends JpaRepository<Tarification, UUID> {
}
