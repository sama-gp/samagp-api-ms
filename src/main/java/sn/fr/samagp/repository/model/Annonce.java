package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "ANNONCES")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
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
