package sn.fr.samagp.services.inter;

import sn.fr.samagp.controller.response.CommentaireResponse;
import sn.fr.samagp.repository.dto.CommentaireDto;

import java.util.List;
import java.util.UUID;

public interface ICommentaireService {
    CommentaireResponse createCommentaire(CommentaireDto commentaireDto);
    List<CommentaireResponse> getCommentairesByAnnonce(UUID annonceId);
    void deleteCommentaire(UUID id);
    public CommentaireResponse updateCommentaire(UUID commentaireId, CommentaireDto commentaireDto);
}
