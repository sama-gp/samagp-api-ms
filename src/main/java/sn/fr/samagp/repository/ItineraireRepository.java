package sn.fr.samagp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.fr.samagp.repository.model.Itinerraire;
import sn.fr.samagp.repository.model.ItinerraireId;

public interface ItineraireRepository extends JpaRepository<Itinerraire, ItinerraireId> {
}
