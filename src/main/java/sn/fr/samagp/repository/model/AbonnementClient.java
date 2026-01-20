package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "abonnements_client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbonnementClient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanAbonnement plan;

    @Column(nullable = false)
    private LocalDateTime dateDebut;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutAbonnement statut; // ACTIF, EXPIRE, SUSPENDU, ANNULE

    private Integer annoncesUtilisees;

    private Boolean renouvellementAuto;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Méthodes utilitaires
    public boolean estActif() {
        return statut == StatutAbonnement.ACTIF &&
                LocalDateTime.now().isBefore(dateFin);
    }

    public boolean peutPublierAnnonce() {
        if (!estActif()) return false;
        return plan.estIllimite() ||
                annoncesUtilisees < plan.getNombreAnnoncesInclus();
    }

    public void incrementerAnnoncesUtilisees() {
        if (annoncesUtilisees == null) annoncesUtilisees = 0;
        annoncesUtilisees++;
    }

    public boolean estExpire() {
        return LocalDateTime.now().isAfter(dateFin);
    }
    public void renouveler(LocalDateTime nouvelleDateFin) {
        this.dateFin = nouvelleDateFin;
        this.statut = StatutAbonnement.ACTIF;

    }

    public void annuler() {
        this.statut = StatutAbonnement.ANNULE;
    }
}

