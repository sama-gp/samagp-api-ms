package sn.fr.samagp.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvisDTO {
    // Ajoute les champs nécessaires ici, par exemple :
    private String commentaire;
    private int note;
}