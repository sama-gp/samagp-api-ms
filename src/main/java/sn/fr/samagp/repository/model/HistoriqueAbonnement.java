package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "historique_abonnements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoriqueAbonnement {

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

    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    @Enumerated(EnumType.STRING)
    private StatutAbonnement statut;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantPaye;

    @Enumerated(EnumType.STRING)
    private MethodePaiement methodePaiement;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String raisonChangement; // "NOUVEAU", "RENOUVELLEMENT", "UPGRADE", "DOWNGRADE"
}