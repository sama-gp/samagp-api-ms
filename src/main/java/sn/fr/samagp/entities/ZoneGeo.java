package sn.fr.samagp.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity (name = "ZONE_GEOGRAPHIQUE")
@Table
@SequenceGenerator(name = "ZG_SEQ", sequenceName = "ZG_SEQ", allocationSize = 1)
public class ZoneGeo  implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "ZG_SEQ")
    @Column (name = "ZG_ID")
    private Long id;
    @Column (name = "ZG_LIBELLE")
    private String libelle;
    @ManyToOne
    @JoinColumn ( name = "ZG_TYPE_ZONE_CODE")
    private TypeZoneGeo type;
    @ManyToOne
    @JoinColumn ( name = "ZG_ZONE_PARENT")
    private ZoneGeo parent;
}
