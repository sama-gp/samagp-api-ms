package sn.fr.samagp.services.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sn.fr.samagp.exceptions.ForbiddenException;
import sn.fr.samagp.mapper.AnnonceMapper;
import sn.fr.samagp.mapper.ClientMapper;
import sn.fr.samagp.mapper.ItineraireMapper;
import sn.fr.samagp.mapper.TarificationMapper;
import sn.fr.samagp.repository.*;
import sn.fr.samagp.repository.dto.AnnonceDTO;
import sn.fr.samagp.repository.dto.ClientDTO;
import sn.fr.samagp.repository.dto.UpdateAnnonceDTO;
import sn.fr.samagp.repository.dto.ZoneGeoDTO;
import sn.fr.samagp.repository.model.*;
import sn.fr.samagp.controller.request.AnnonceSearchCriteria;
import sn.fr.samagp.controller.response.AnnonceResponse;
import sn.fr.samagp.services.inter.IAnnonce;
import sn.fr.samagp.services.inter.ISecurityService;
import sn.fr.samagp.validator.AnnonceValidator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnnonceServiceImp implements IAnnonce {

    private final AnnonceRepository annonceRepository;
    private final AnnonceMapper annonceMapper;
    private final AnnonceValidator annonceValidator;
    private final ClientMapper clientMapper;
    private final TarificationMapper tarificationMapper;
    private final TarificationRepository tarificationRepository;
    private final ClientRepository clientRepository;
    private final ItineraireMapper itineraireMapper;
    private final ItineraireRepository itineraireRepository;
    private final ZoneGeoRepository zoneGeoRepository;
    private final ISecurityService securityService;

    @Override
    @Transactional
    public AnnonceResponse createAnnonce(AnnonceDTO annonceDTO) {
        // Récupérer l'utilisateur authentifié depuis Keycloak
        JwtAuthenticationToken authentication = securityService.getAuthentication();
        Jwt jwt = authentication.getToken();
        String userId = jwt.getSubject();
        String userEmail = jwt.getClaimAsString("email");

        if (!userEmail.equals(annonceDTO.clientDTO().getEmail())) {
            throw new ForbiddenException("Vous ne pouvez pas créer d'annonce pour un autre utilisateur");
        }

        // Gestion du client
        Client client = clientRepository.findByEmail(userEmail)
                .orElseGet(() -> {
                    Client newClient = clientMapper.toEntity(annonceDTO.clientDTO());
                    newClient.setKeycloakId(userId);
                    newClient.setEmail(userEmail);
                    newClient.setFirstName(jwt.getClaimAsString("given_name"));
                    newClient.setLastName(jwt.getClaimAsString("family_name"));
                    return clientRepository.save(newClient);
                });

        // Vérification que le client a bien un keycloakId
        if (client.getKeycloakId() == null || client.getKeycloakId().isBlank()) {
            client.setKeycloakId(userId);
            client = clientRepository.save(client);
        }


        // Map annonce
        Annonce annonce = annonceMapper.toEntity(annonceDTO);
        annonce.setClient(client);

        if (annonceDTO.itineraireDTO() != null){
            Itinerraire itinerraire = itineraireMapper.toEntity(annonceDTO.itineraireDTO());
            // Assurez-vous que les zones géo existent en base
            if (itinerraire.getDepart() != null && itinerraire.getDepart().getId() == null) {
                itinerraire.setDepart(zoneGeoRepository.save(itinerraire.getDepart()));
            }
            if (itinerraire.getArrivee() != null && itinerraire.getArrivee().getId() == null) {
                itinerraire.setArrivee(zoneGeoRepository.save(itinerraire.getArrivee()));
            }

            // Initialisez manuellement l'ID composite
            itinerraire.setId(new ItinerraireId(
                    itinerraire.getDepart().getId(),
                    itinerraire.getArrivee().getId()
            ));

            itinerraire = itineraireRepository.save(itinerraire);
            annonce.setItineraire(itinerraire);
        }

        if(annonceDTO.tarificationDTO() != null){
            // Map tarification et sauvegarder explicitement si pas de cascade
            Tarification tarification = tarificationMapper.toEntity(annonceDTO.tarificationDTO());
            if (tarification.getFraisSupplementaires() != null) {
                for (FraisSupplementaire fs : tarification.getFraisSupplementaires()) {
                    fs.setTarification(tarification);
                }
            }
            tarification = tarificationRepository.save(tarification);
            annonce.setTarification(tarification);
        }


        annonceValidator.validate(annonce);
        annonce = annonceRepository.save(annonce);
        return annonceMapper.toResponse(annonce);
    }


    private Client synchronizeClient(ClientDTO clientDTO, Jwt jwt) {
        String keycloakUserId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");

        return clientRepository.findByKeycloakId(keycloakUserId)
                .orElseGet(() -> {
                    // Créer un nouveau client s'il n'existe pas
                    Client newClient = clientMapper.toEntity(clientDTO);

                    // Compléter avec les infos de Keycloak
                    newClient.setKeycloakId(keycloakUserId);
                    newClient.setEmail(email);
                    newClient.setFirstName(jwt.getClaimAsString("given_name"));
                    newClient.setLastName(jwt.getClaimAsString("family_name"));

                    // Valeurs par défaut si non fournies
                    if (newClient.getProfile() == null) {
                        newClient.setProfile(Profile.USER); // ou GP selon votre logique
                    }

                    return clientRepository.save(newClient);
                });
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
    public AnnonceDTO updateAnnonce(UUID id, UpdateAnnonceDTO updateDTO) {
        return annonceRepository.findById(id)
                .map(existingAnnonce -> {
                    // Log avant mise à jour
                    log.info("Avant mise à jour - Annonce: {}", existingAnnonce);
                    log.info("Avant mise à jour - updateDTO: {}", updateDTO);

                    // Mise à jour

                    annonceMapper.updateAnnonceFromDto(updateDTO, existingAnnonce);
                    existingAnnonce.setUpdatedAt(LocalDateTime.now());
                    if(updateDTO.tarificationDTO() != null) {
                        if(existingAnnonce.getTarification() == null) {
                            // Créer une nouvelle tarification
                            Tarification newTarif = tarificationMapper.toEntity(updateDTO.tarificationDTO());
                            existingAnnonce.setTarification(newTarif);
                        } else {
                            // Mettre à jour la tarification existante
                            Tarification existingTarif = existingAnnonce.getTarification();
                            existingTarif.setPrixParKg(updateDTO.tarificationDTO().prixParKg());
                            existingTarif.setDevise(Devise.valueOf(updateDTO.tarificationDTO().devise()));
                            existingAnnonce.setTarification(existingTarif);
                        }
                    }

                    // Log après mise à jour avant sauvegarde
                    log.info("Après mise à jour - Annonce: {}", existingAnnonce);

                    Annonce saved = annonceRepository.save(existingAnnonce);

                    // Log après sauvegarde
                    log.info("Après sauvegarde - Annonce: {}", saved);

                    return annonceMapper.toDto(saved);
                })
                .orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }


//    @Override
//    public AnnonceDTO updateAnnonce(UUID id, UpdateAnnonceDTO updateDTO) {
//        return annonceRepository.findById(id)
//                .map(existingAnnonce -> {
//                    // Log avant modification
//                    log.info("Annonce avant modification - ID: {}", existingAnnonce.getId());
//
//                    // 1. Mise à jour des champs simples
//                    existingAnnonce.setDescription(updateDTO.description());
//                    existingAnnonce.setItineraireDetailsDepart(updateDTO.itineraireDetailsDepart());
//                    existingAnnonce.setItineraireDetailsArrive(updateDTO.itineraireDetailsArrive());
//                    existingAnnonce.setDateDepart(updateDTO.dateDepart());
//                    existingAnnonce.setDateArrive(updateDTO.dateArrive());
//                    existingAnnonce.setUpdatedAt(LocalDateTime.now());
//
//                    // 2. Mise à jour de l'itinéraire (sans recréer une nouvelle instance)
//                    if (updateDTO.itineraireDTO() != null && existingAnnonce.getItineraire() != null) {
//                        // Conversion manuelle pour le départ
//                        if (updateDTO.itineraireDTO().depart() != null) {
//                            ZoneGeo depart = convertZoneGeoDTOToEntity(updateDTO.itineraireDTO().depart());
//                            existingAnnonce.getItineraire().setDepart(depart);
//                        }
//
//                        // Conversion manuelle pour l'arrivée
//                        if (updateDTO.itineraireDTO().arrivee() != null) {
//                            ZoneGeo arrivee = convertZoneGeoDTOToEntity(updateDTO.itineraireDTO().arrivee());
//                            existingAnnonce.getItineraire().setArrivee(arrivee);
//                        }
//                    }
//
//                    // 3. Mise à jour de la tarification (gestion manuelle)
//                    if (updateDTO.tarificationDTO() != null) {
//                        if (existingAnnonce.getTarification() == null) {
//                            existingAnnonce.setTarification(new Tarification());
//                        }
//
//                        Tarification tarification = existingAnnonce.getTarification();
//                        tarification.setPrixParKg(updateDTO.tarificationDTO().prixParKg());
//                        tarification.setDevise(Devise.valueOf(updateDTO.tarificationDTO().devise()));
//
//                        // Gestion des frais supplémentaires avec vérification des null
//                        if (updateDTO.tarificationDTO().fraisSupplementaires() != null) {
//                            // Supprime les anciens frais
//                            tarification.getFraisSupplementaires().clear();
//
//                            // Ajoute les nouveaux frais
//                            updateDTO.tarificationDTO().fraisSupplementaires().stream()
//                                    .filter(Objects::nonNull)
//                                    .forEach(fraisDTO -> {
//                                        FraisSupplementaire frais = new FraisSupplementaire();
//                                        frais.setType(fraisDTO.type());
//                                        frais.setPrix(fraisDTO.prix());
//                                        tarification.addFraisSupplementaire(frais);
//                                    });
//                        }
//                    }
//
//                    // Sauvegarde et log
//                    Annonce updatedAnnonce = annonceRepository.save(existingAnnonce);
//                    log.info("Annonce après modification - ID: {}", updatedAnnonce.getId());
//
//                    return annonceMapper.toDto(updatedAnnonce);
//                })
//                .orElseThrow(() -> new RuntimeException("Annonce non trouvée avec l'ID: " + id));
//    }

    // Méthode helper pour la conversion
    private ZoneGeo convertZoneGeoDTOToEntity(ZoneGeoDTO dto) {
        ZoneGeo entity = new ZoneGeo();
        entity.setId(dto.id());
        entity.setLibelle(dto.libelle());

        // Conversion du type si nécessaire
        if (dto.type() != null) {
            TypeZoneGeo type = new TypeZoneGeo();
            type.setCode(dto.type().code());
            type.setLibelle(dto.libelle());
            entity.setType(type);
        }

        // Conversion du parent si nécessaire
        if (dto.parent() != null) {
            ZoneGeo parent = new ZoneGeo();
            parent.setId(dto.parent().id());
            parent.setLibelle(dto.parent().libelle());
            entity.setParent(parent);
        }

        return entity;
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

    @Override
    public List<AnnonceResponse> getAnnoncesByUser(UUID userId) {
        Client client = clientRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé"));

        List<Annonce> annonces = annonceRepository.findByClientOrderByDateDepartDesc(client);
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnnonceResponse> getAnnoncesToday() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        List<Annonce> annonces = annonceRepository.findByDateDepartBetween(startOfDay, endOfDay);
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnnonceResponse> getAnnoncesThisWeek() {
        LocalDateTime startOfWeek = LocalDateTime.now().with(DayOfWeek.MONDAY).with(LocalTime.MIN);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).with(LocalTime.MAX);

        List<Annonce> annonces = annonceRepository.findByDateDepartBetween(startOfWeek, endOfWeek);
        return annonces.stream()
                .map(annonceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AnnonceResponse> getAnnoncesByUserEmail(String email) {
        // 1. Valider l'email
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // 2. Trouver l'utilisateur (via UserService)
        Client user = clientRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        // 3. Retourner les annonces
        return annonceRepository.findByClientId(user.getId())
                .stream()
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
