package sn.fr.samagp.services;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.ItineraireRepository;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.model.Itineraire;

import java.util.List;

@Service
public class EntityRetrievalService {

    private final AnnonceRepository annonceRepository;
    private final ItineraireRepository itineraireRepository;

    public EntityRetrievalService(AnnonceRepository annonceRepository, ItineraireRepository itineraireRepository) {
        this.annonceRepository = annonceRepository;
        this.itineraireRepository = itineraireRepository;
    }

    // Récupérer les annonces par leurs IDs
    public List<Annonce> getAnnoncesByIds(List<Long> ids) {
        return ids != null ? annonceRepository.findAllById(ids) : List.of();
    }

    // Récupérer les itinéraires favoris par leurs IDs
    public List<Itineraire> getItinerairesByIds(List<Long> ids) {
        return ids != null ? itineraireRepository.findAllById(ids) : List.of();
    }
}
