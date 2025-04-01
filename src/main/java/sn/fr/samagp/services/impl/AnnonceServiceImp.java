package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.fr.samagp.mapper.AnnonceMapper;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.response.AnnonceResponse;
import sn.fr.samagp.services.inter.IAnnonce;
import sn.fr.samagp.validator.AnnonceValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnonceServiceImp implements IAnnonce {

    private final AnnonceRepository annonceRepository;
    private final AnnonceMapper annonceMapper;
    private final AnnonceValidator annonceValidator;
    private final ClientMapper clientMapper;

    public AnnonceServiceImp(AnnonceRepository annonceRepository, AnnonceMapper annonceMapper, AnnonceValidator annonceValidator, ClientMapper clientMapper) {
        this.annonceRepository = annonceRepository;
        this.annonceMapper = annonceMapper;
        this.annonceValidator = annonceValidator;
        this.clientMapper = clientMapper;
    }


    @Override
    public AnnonceResponse createAnnonce(AnnonceDTO annonceDTO) {
        Annonce annonce = annonceMapper.toEntity(annonceDTO);
        annonceValidator.validate(annonce);
        return annonceMapper.toResponse(annonceRepository.save(annonce));
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
                    existingAnnonce.setItineraireDetailsDepart(annonceDTO.getItineraireDetailsDepart());
                    existingAnnonce.setItineraireDetailsArrive(annonceDTO.getItineraireDetailsArrive());
                    existingAnnonce.setDateDepart(annonceDTO.getDateDepart());
                    existingAnnonce.setDateArrive(annonceDTO.getDateArrive());
                    existingAnnonce.setUpdatedAt(LocalDateTime.now());
                    existingAnnonce.setDescription(annonceDTO.getDescription());
                    existingAnnonce.setClient(clientMapper.toEntity(annonceDTO.getClientDTO()));
                    return annonceMapper.toDto(annonceRepository.save(existingAnnonce));
                }).orElseThrow(() -> new RuntimeException("Annonce non trouvée!"));
    }

    @Override
    public void deleteAnnonce(UUID id) {
        Optional<Annonce> annonceToDelete = annonceRepository.findById(id);
        if (annonceToDelete.isPresent()) {
            System.out.println("************ Suppression de l'annonce : " + annonceToDelete.get());
            annonceRepository.deleteById(id);
        } else {
            System.out.println("************** Annonce avec l'ID " + id + " introuvable !");
            throw new RuntimeException("Annonce non trouvée!");
        }
    }

    @Override
    public List<AnnonceResponse> getAnnonceByClient(UUID idClient) {
        return annonceRepository.findByClientId(idClient).stream()
                .map(annonceMapper::toResponse)
                .collect(Collectors.toList());
    }
}
