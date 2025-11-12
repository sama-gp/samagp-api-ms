package sn.fr.samagp.services.inter;

import org.springframework.data.jpa.domain.Specification;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.controller.request.AnnonceSearchCriteria;
import sn.fr.samagp.controller.response.AnnonceResponse;
import sn.fr.samagp.repository.dto.UpdateAnnonceDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAnnonce {
    AnnonceResponse createAnnonce(AnnonceDTO annonceDTO);
    Optional<AnnonceResponse> getAnnonceById(UUID id);
    List<AnnonceResponse> getAllAnnonces();
    AnnonceDTO updateAnnonce(UUID id, UpdateAnnonceDTO annonceDTO);
    void deleteAnnonce(UUID id);
    List<AnnonceResponse> getAnnonceByKeycloakClient(String keycloakId);
    List<AnnonceResponse> filterByCriteria(AnnonceSearchCriteria criteria);
    List<AnnonceResponse> getAnnoncesByUser(UUID userId);
    List<AnnonceResponse> getAnnoncesToday();
    List<AnnonceResponse> getAnnoncesThisWeek();
    List<AnnonceResponse> getAnnoncesByUserEmail(String email);

}
