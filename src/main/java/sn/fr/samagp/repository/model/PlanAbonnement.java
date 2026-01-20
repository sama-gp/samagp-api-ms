package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "plans_abonnement", uniqueConstraints = {
        @UniqueConstraint(name = "uk_plan_code", columnNames = "code")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanAbonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(unique = true)
    private String code; // "BASIC", "PREMIUM", "PRO"

    @NotBlank
    private String nom;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DureeAbonnement duree; // MENSUEL, SEMESTRIEL, ANNUEL

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal prix;

    @Enumerated(EnumType.STRING)
    private Devise devise;

    private Integer nombreAnnoncesInclus;

    private Boolean actif;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean estIllimite() {
        return nombreAnnoncesInclus == null;
    }

    public void activer() {
        this.actif = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void initialiserMetadonnees() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }


}

