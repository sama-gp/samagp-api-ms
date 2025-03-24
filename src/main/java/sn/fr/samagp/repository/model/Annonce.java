package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "annonce")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private Itineraire itineraire;
    @NotBlank(message = "the ItineraireDetailsDepart is required")
    private String ItineraireDetailsDepart;
    @NotBlank(message = "the ItineraireDetailsArrive is required")
    private String ItineraireDetailsArrive;
    @NotBlank(message = "the description is required")
    private String description;
    @NotNull
    private LocalDateTime dateDepart;
    @NotNull
    private LocalDateTime dateArrive;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avis> avis = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
