package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "FRAIS_SUPPLEMENTAIRES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tarification")
public class FraisSupplementaire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    private String type; // ex: "Passeport", "Permis", etc.

    @NotNull
    private Double prix; // en euros

    @ManyToOne
    @JoinColumn(name = "tarification_id")
    private Tarification tarification;
}
