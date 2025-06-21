package sn.fr.samagp.repository.dto;

import java.util.List;
import java.util.UUID;

public record TarificationDTO(
        UUID id,
        Double prixParKg,
        String devise,
        List<FraisSupplementaireDTO> fraisSupplementaires
) {}