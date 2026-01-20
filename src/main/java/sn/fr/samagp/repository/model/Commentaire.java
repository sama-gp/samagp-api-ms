package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "commentaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commentaire {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    @Column(nullable = false, columnDefinition = "text")
    private String contenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "annonce_id", nullable = false)
    private Annonce annonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "L'auteur du commentaire est obligatoire")
    private Client auteur;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Commentaire that = (Commentaire) o;
        return Objects.equals(id, that.id) && Objects.equals(contenu, that.contenu) && Objects.equals(annonce, that.annonce) && Objects.equals(auteur, that.auteur) && Objects.equals(dateCreation, that.dateCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contenu, annonce, auteur, dateCreation);
    }

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreation;

    // Constructeur pratique
    public Commentaire(String contenu, Client auteur, Annonce annonce) {
        this.contenu = contenu;
        this.auteur = auteur;
        this.annonce = annonce;
    }
}