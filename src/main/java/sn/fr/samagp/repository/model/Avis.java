package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "AVIS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "annonce_id", nullable = false)
    private Annonce annonce;
    @NotBlank(message = "the idClient is required")
    private Long idClient; // Un avis concerne un client inscris dans la base de données
}
