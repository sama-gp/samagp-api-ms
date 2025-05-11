package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "ZONES_GEOS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class ZoneGeo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String libelle;
    @ManyToOne
    @JoinColumn()
    private TypeZoneGeo type;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ZoneGeo parent;
}
