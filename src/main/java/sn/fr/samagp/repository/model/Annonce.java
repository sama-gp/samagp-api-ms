package sn.fr.samagp.repository.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
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
@ToString(exclude = {"tarification", "avis", "client"})
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    //@Valid
    //@NotNull(message = "L'itinéraire est requis")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "IT_ZN_DEPART", referencedColumnName = "IT_ZN_DEPART"),
            @JoinColumn(name = "IT_ZN_ARRIVEE", referencedColumnName = "IT_ZN_ARRIVEE")
    })
    private Itinerraire itineraire;
    @Column(name = "itineraire_depart_details")
    private String itineraireDetailsDepart;

    @Column(name = "itineraire_arrivee_details")
    private String itineraireDetailsArrive;

    @Column()
    @NotBlank(message = "Les détails du départ sont requis")
    private String description;
    @NotNull(message = "La date de depart est requise")
    private LocalDateTime dateDepart;
    @NotNull(message = "La date d'arrivée est requise")
    private LocalDateTime dateArrive;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avis> avis = new ArrayList<>();

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Commentaire> commentaires = new ArrayList<>();

    public void addCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
        commentaire.setAnnonce(this);
        commentaire.getAuteur().getCommentaires().add(commentaire);
    }

    public void removeCommentaire(Commentaire commentaire) {
        commentaires.remove(commentaire);
        commentaire.setAnnonce(null);
        if (commentaire.getAuteur() != null) {
            commentaire.getAuteur().getCommentaires().remove(commentaire);
        }
    }


    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tarification_id", referencedColumnName = "id")
    private Tarification tarification;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
