package sn.fr.samagp.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import sn.fr.samagp.controller.response.CommentaireResponse;
import sn.fr.samagp.exceptions.ResourceNotFoundException;
import sn.fr.samagp.mapper.CommentaireMapper;
import sn.fr.samagp.repository.AnnonceRepository;
import sn.fr.samagp.repository.ClientRepository;
import sn.fr.samagp.repository.CommentaireRepository;
import sn.fr.samagp.repository.dto.CommentaireDto;
import sn.fr.samagp.repository.model.Annonce;
import sn.fr.samagp.repository.model.Client;
import sn.fr.samagp.repository.model.Commentaire;
import sn.fr.samagp.services.inter.ICommentaireService;
import sn.fr.samagp.services.inter.ISecurityService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentaireImpl implements ICommentaireService {

    private static final Logger log = LoggerFactory.getLogger(CommentaireImpl.class);
    private final CommentaireRepository commentaireRepository;
    private final AnnonceRepository annonceRepository;
    private final ClientRepository clientRepository;
    private final CommentaireMapper commentaireMapper;
    private final ISecurityService securityService;

    @Override
    public CommentaireResponse createCommentaire(CommentaireDto commentaireDto) {
        JwtAuthenticationToken authentication = securityService.getAuthentication();
        Jwt jwt = authentication.getToken();
        String userId = jwt.getSubject();

        Client auteur = clientRepository.findByKeycloakId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Client non trouvé avec l'ID Keycloak: " + userId));

        Annonce annonce = annonceRepository.findById(commentaireDto.getAnnonceId())
                .orElseThrow(() -> new ResourceNotFoundException("Annonce non trouvée"));

        Commentaire commentaire = commentaireMapper.toEntity(commentaireDto);
        commentaire.setAuteur(auteur);
        commentaire.setAnnonce(annonce);

        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toResponse(savedCommentaire);
    }

    @Override
    public List<CommentaireResponse> getCommentairesByAnnonce(UUID annonceId) {
        return commentaireRepository.findByAnnonceIdOrderByDateCreationDesc(annonceId)
                .stream()
                .map(commentaireMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommentaire(UUID id) {
        commentaireRepository.deleteById(id);
    }

    @Override
    public CommentaireResponse updateCommentaire(UUID commentaireId, CommentaireDto commentaireDto) {
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new ResourceNotFoundException("Commentaire non trouvé avec l'ID: " + commentaireId));

        // 2. Récupérer l'utilisateur authentifié
        JwtAuthenticationToken authentication = securityService.getAuthentication();
        Jwt jwt = authentication.getToken();
        String userId = jwt.getSubject();

        if (!commentaire.getAuteur().getKeycloakId().equals(userId)) {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier ce commentaire");
        }
        commentaire.setContenu(commentaireDto.getContenu());
        Commentaire updatedCommentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toResponse(updatedCommentaire);
    }
}
