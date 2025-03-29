package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.fr.samagp.mapper.AnnonceMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.response.AnnonceResponse;
import sn.fr.samagp.services.inter.IAnnonce;
import sn.fr.samagp.validator.AnnonceValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnonceServiceImp implements IAnnonce {

    private final AnnonceRepository annonceRepository;
    private final AnnonceMapper annonceMapper;
    private final AnnonceValidator annonceValidator;

    public AnnonceServiceImp(AnnonceRepository annonceRepository, AnnonceMapper annonceMapper, AnnonceValidator annonceValidator) {
        this.annonceRepository = annonceRepository;
        this.annonceMapper = annonceMapper;
        this.annonceValidator = annonceValidator;
    }


    @Override
    public AnnonceDTO createAnnonce(AnnonceDTO annonceDTO) {
        Annonce annonce = annonceMapper.toEntity(annonceDTO);
        annonceValidator.validate(annonce);
        return annonceMapper.toDto(annonceRepository.save(annonce));
    }

    @Override
    public Optional<AnnonceResponse> getAnnonceById(UUID id) {
        return annonceRepository.findById(id).map(annonceMapper::toResponse);
    }

    @Override
    public List<AnnonceResponse> getAllAnnonces() {
        return annonceRepository.findAll().stream()
                .map(annonceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AnnonceDTO updateAnnonce(UUID id, AnnonceDTO annonceDTO) {
        return annonceRepository.findById(id)
                .map(existingAnnonce -> {
//                    existingAnnonce.setItineraire(itineraireMapper.toEntity(annonceDTO.itineraireDTO()));
//                    existingAnnonce.setItineraireDetailsDepart(annonceDTO.itineraireDetailsDepart());
//                    existingAnnonce.setItineraireDetailsArrive(annonceDTO.itineraireDetailsArrive());
//                    existingAnnonce.setDateDepart(annonceDTO.dateDepart());
//                    existingAnnonce.setDateArrive(annonceDTO.dateArrive());
//                    existingAnnonce.setUpdatedAt(LocalDateTime.now());
                    return annonceMapper.toDto(annonceRepository.save(existingAnnonce));
                }).orElseThrow(() -> new RuntimeException("Annonce non trouvée!"));
    }

    @Override
    public void deleteAnnonce(UUID id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public List<AnnonceResponse> getAnnonceByClient(UUID idClient) {
        return annonceRepository.findByClientId(idClient).stream()
                .map(annonceMapper::toResponse)
                .collect(Collectors.toList());
    }
}
