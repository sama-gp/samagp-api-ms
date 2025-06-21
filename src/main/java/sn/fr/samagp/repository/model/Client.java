package sn.fr.samagp.repository.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    private String lastName;
    @Email(message = "The email must be valid")
    @NotBlank(message = "The email is required")
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Profile profile;

    @ElementCollection
    @CollectionTable(name = "client_phones", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "phone_number")
    private List<String> phone = new ArrayList<>();


    @ElementCollection
    @CollectionTable(name = "client_addresses", joinColumns = @JoinColumn(name = "client_id"))
    private List<Adresse> address = new ArrayList<>();;

    @OneToMany(mappedBy = "client", cascade = CascadeType.DETACH)
    private List<Annonce> annonces = new ArrayList<>();

    @OneToMany(mappedBy = "auteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Commentaire> commentaires = new ArrayList<>();

    @Column(name = "keycloak_id", unique = true, nullable = false)
    @NotBlank
    @EqualsAndHashCode.Include
    private String keycloakId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void addCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
        commentaire.setAuteur(this);
    }

    public void removeCommentaire(Commentaire commentaire) {
        commentaires.remove(commentaire);
        commentaire.setAuteur(null);
    }

    // Méthode pour obtenir le nom complet
    public String getFullName() {
        return firstName + " " + lastName;
    }

//    @ManyToMany
//    @JoinTable(
//            name = "favoris", // Nom de la table intermédiaire
//            joinColumns = @JoinColumn(name = "client_id"), // Clé étrangère pour Client
//            inverseJoinColumns = @JoinColumn(name = "itineraire_id") // Clé étrangère pour Itinéraire
//    )
//    @JsonIgnore
//    private List<Itineraire> favoris = new ArrayList<>();



}
