package sn.fr.samagp.repository.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    // Nouveaux champs pour les pièces d'identité
    @Enumerated(EnumType.STRING)
    @Column(name = "identity_document_type")
    private TypePieces typePieces;

    @Column(name = "pieces_recto")
    private String piecesRecto;

    @Column(name = "pieces_verso")
    private String piecesVerso;

    @Column(name = "ninea", unique = true)
    private String ninea;

    // Champ pour la validation par le super utilisateur
    @Column(name = "is_valid")
    private boolean isValid = false;

    @Column(name = "keycloak_id", unique = true, nullable = false)
    @NotBlank
    @EqualsAndHashCode.Include
    private String keycloakId;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    private String profilePictureUrl;
    @ManyToMany
    @JoinTable(
            name = "client_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    @JsonIgnore
    private Set<Client> following = new HashSet<>();
    @ManyToMany(mappedBy = "following")
    @JsonIgnore
    private Set<Client> followers = new HashSet<>();
    public void follow(Client clientToFollow) {
        this.following.add(clientToFollow);
        clientToFollow.getFollowers().add(this);
    }
    public void unfollow(Client clientToUnfollow) {
        this.following.remove(clientToUnfollow);
        clientToUnfollow.getFollowers().remove(this);
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


    // Méthode pour vérifier si tous les documents sont fournis
    public boolean hasAllDocuments() {
        if (typePieces == null) return false;
        if (piecesRecto == null || piecesRecto.isEmpty()) return false;
        if (typePieces == TypePieces.CARTE_NATIONALE && (piecesVerso == null || piecesVerso.isEmpty())) {
            return false;
        }
        if (profile == Profile.GP && (ninea == null || ninea.isEmpty())) {
            return false;
        }
        return true;
    }

    public boolean canCreateAnnonces() {
        return Boolean.TRUE.equals(this.isValid);
    }

    public void markAsPendingValidation() {
        this.isValid = false;
    }

    public void validate() {
        if (this.hasAllDocuments()) {
            this.isValid = true;
        } else {
            throw new IllegalStateException("Le client n'a pas fourni tous les documents requis");
        }
    }

    public void rejectValidation() {
        this.isValid = false;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Client client = (Client) o;
        return getId() != null && Objects.equals(getId(), client.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
