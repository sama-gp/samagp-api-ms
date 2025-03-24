package sn.fr.samagp.repository;

import org.springframework.data.repository.Repository;
import sn.fr.samagp.repository.model.Itineraire;

import java.util.List;

public interface ItineraireRepository  extends Repository<Long, Itineraire> {
    List<Itineraire> findAllById(List<Long> ids);
}
