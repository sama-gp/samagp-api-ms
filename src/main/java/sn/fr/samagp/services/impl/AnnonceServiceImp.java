package sn.fr.samagp.services.impl;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sn.fr.samagp.mapper.AnnonceMapper;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.controller.request.AnnonceSearchCriteria;
import sn.fr.samagp.controller.response.AnnonceResponse;
import sn.fr.samagp.services.inter.IAnnonce;
import sn.fr.samagp.validator.AnnonceValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnonceServiceImp implements IAnnonce {

    private final AnnonceRepository annonceRepository;
    private final AnnonceMapper annonceMapper;
    private final AnnonceValidator annonceValidator;
    private final ClientMapper clientMapper;


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
                    existingAnnonce.setDateDepart(annonceDTO.dateDepart());
                    existingAnnonce.setDateArrive(annonceDTO.dateArrive());
                    existingAnnonce.setDescription(annonceDTO.description());
                    return annonceMapper.toDto(annonceRepository.save(existingAnnonce));
                }).orElseThrow(() -> new RuntimeException("Annonce non trouvée!"));
    }

    @Override
    public void deleteAnnonce(UUID id) {
        Optional<Annonce> annonceToDelete = annonceRepository.findById(id);
        if (annonceToDelete.isPresent()) {
            log.info("************ Suppression de l'annonce : " + annonceToDelete.get());
            annonceRepository.deleteById(id);
        } else {
            log.info("************** Annonce avec l'ID " + id + " introuvable !");
            throw new RuntimeException("Annonce non trouvée!");
        }
    }

    @Override
    public List<AnnonceResponse> getAnnonceByClient(UUID idClient) {
        return annonceRepository.findByClientId(idClient).stream()
                .map(annonceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnnonceResponse> filterByCriteria(AnnonceSearchCriteria criteria) {
         Specification<Annonce> spec = buildAnnonceCriteriaQuerySpecifications(criteria);

         return  annonceRepository.findAll(spec).stream()
                 .map(annonceMapper::toResponse)
                 .collect(Collectors.toList());
    }
    private Specification<Annonce> buildAnnonceCriteriaQuerySpecifications (AnnonceSearchCriteria criteria){
      return   (criteriaRoot, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.itineraireId() != null)
                predicates.add(criteriaBuilder.equal(criteriaRoot.get("itineraire").get("id"), criteria.itineraireId()));

            if (criteria.dateDepart() != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaRoot.get("dateDepart"), criteria.dateDepart()));
            if (criteria.dateArrivee() != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(criteriaRoot.get("dateArrive"), criteria.dateArrivee()));

            if (criteria.depart() != null)
                predicates.add(criteriaBuilder.equal(criteriaRoot.get("itineraire").get("id").get("departId"), criteria.depart().id()));
            if (criteria.arrivee() != null)
                predicates.add(criteriaBuilder.equal(criteriaRoot.get("itineraire").get("id").get("arriveeId"), criteria.arrivee().id()));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
