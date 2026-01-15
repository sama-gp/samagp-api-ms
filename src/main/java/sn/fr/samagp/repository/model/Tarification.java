package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TARIFICATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"fraisSupplementaires", "annonce"})
public class Tarification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    private Double prixParKg; // en euros

    @Enumerated(EnumType.STRING)
    private Devise devise;

    @OneToMany(mappedBy = "tarification", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FraisSupplementaire> fraisSupplementaires = new ArrayList<>();

    @OneToOne(mappedBy = "tarification")
    private Annonce annonce;

    public double getPrixTotal(double poidsKg) {
        return prixParKg * poidsKg;
    }

    public double convertirEnXof(double taux) {
        return prixParKg * taux;
    }

    public void addFraisSupplementaire(FraisSupplementaire frais) {
        frais.setTarification(this);
        this.fraisSupplementaires.add(frais);
    }
}

