package sn.fr.samagp.services.inter;

import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.response.AnnonceResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IAnnonce {
    AnnonceResponse createAnnonce(AnnonceDTO annonceDTO);
    Optional<AnnonceResponse> getAnnonceById(UUID id);
    List<AnnonceResponse> getAllAnnonces();
    AnnonceDTO updateAnnonce(UUID id, AnnonceDTO annonceDTO);
    void deleteAnnonce(UUID id);
    List<AnnonceResponse> getAnnonceByClient(UUID idClient);
}
