package sn.fr.samagp.repository.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private AdresseType type;
    private Boolean isPrincipal;


    // Methode Metier SenGP
    public boolean estVide() {
        return (adresse1 == null || adresse1.isBlank())
                && (adresse2 == null || adresse2.isBlank())
                && (ville == null || ville.isBlank())
                && (codePostal == null || codePostal.isBlank())
                && (pays == null || pays.isBlank());
    }
    public boolean estPrincipal() {
        return Boolean.TRUE.equals(isPrincipal);
    }
    public String formatageUneLigne() {
        StringBuilder sb = new StringBuilder();

        if (adresse1 != null && !adresse1.isBlank()) {
            sb.append(adresse1);
        }

        if (adresse2 != null && !adresse2.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(adresse2);
        }

        if (codePostal != null && !codePostal.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(codePostal);
        }

        if (ville != null && !ville.isBlank()) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(ville);
        }

        if (pays != null && !pays.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(pays);
        }

        return sb.toString();
    }
    public String formatagePlusieursLignes() {
        StringBuilder sb = new StringBuilder();

        if (adresse1 != null && !adresse1.isBlank()) {
            sb.append(adresse1).append("\n");
        }

        if (adresse2 != null && !adresse2.isBlank()) {
            sb.append(adresse2).append("\n");
        }

        if (codePostal != null && ville != null) {
            if (!codePostal.isBlank() && !ville.isBlank()) {
                sb.append(codePostal).append(" ").append(ville).append("\n");
            } else if (!ville.isBlank()) {
                sb.append(ville).append("\n");
            }
        }

        if (pays != null && !pays.isBlank()) {
            sb.append(pays);
        }

        return sb.toString().trim();
    }
    public boolean codePostalValideFormatFrance() {
        if (!"France".equalsIgnoreCase(this.pays)) {
            return true;
        }
        return codePostal != null && codePostal.matches("\\d{5}");
    }

    public String formatageAbrege() {
        StringBuilder sb = new StringBuilder();

        if (adresse1 != null && !adresse1.isBlank()) {

            String adresse1Court = adresse1.length() > 30
                    ? adresse1.substring(0, 27) + "..."
                    : adresse1;
            sb.append(adresse1Court);
        }

        if (ville != null && !ville.isBlank()) {
            if (!sb.isEmpty()) sb.append(", ");
            sb.append(ville);
        }

        return sb.toString();
    }
}
