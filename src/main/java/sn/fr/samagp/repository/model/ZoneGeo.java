package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ZoneGeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String nameZone;
    @Enumerated(EnumType.STRING)
    private TypeZoneGeo typeZoneGeo;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ZoneGeo parent;
}
