package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "paiements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "abonnement_client_id")
    private AbonnementClient abonnementClient;

    @Column(nullable = false, unique = true)
    private String reference; // Référence unique du paiement

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Devise devise;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MethodePaiement methode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPaiement statut;

    private String idTransactionFournisseur; // ID de la transaction chez le processeur de paiement

    @Column(columnDefinition = "TEXT")
    private String detailsTransaction; // Réponse brute du processeur

    @Column(nullable = false)
    private LocalDateTime datePaiement;

    private LocalDateTime dateConfirmation;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
