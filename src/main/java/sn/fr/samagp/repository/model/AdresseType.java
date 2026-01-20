package sn.fr.samagp.repository.model;

import lombok.Getter;

@Getter
public enum AdresseType {
    DOMICILE("Domicile"),
    TRAVAIL("Travail"),
    AUTRE("Autre");

    private final String libelle;

    AdresseType(String libelle) {
        this.libelle = libelle;
    }

}
