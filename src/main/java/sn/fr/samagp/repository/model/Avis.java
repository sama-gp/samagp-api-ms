package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "avis")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "annonce_id", nullable = false)
    private Annonce annonce;
    @NotBlank(message = "the idClient is required")
    private Long idClient; // Un avis concerne un client inscris dans la base de données
}
