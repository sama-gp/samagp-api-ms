package sn.fr.samagp.repository.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Adresse implements Serializable {

    private String adresse1;
    private String adresse2;
    private String ville;
    private String codePostal;
    private String pays;
    private AdresseType type;
    private Boolean isPrincipal;
}
