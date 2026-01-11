package sn.fr.samagp.repository.dto;

import java.util.UUID;

public record FraisSupplementaireDTO(
        UUID id,
        String type,
        Double prix
) {}