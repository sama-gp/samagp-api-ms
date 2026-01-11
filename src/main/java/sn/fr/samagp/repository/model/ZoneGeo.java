package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "zones_geos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneGeo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "type_code", referencedColumnName = "code")
    private TypeZoneGeo type;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ZoneGeo parent;
}
