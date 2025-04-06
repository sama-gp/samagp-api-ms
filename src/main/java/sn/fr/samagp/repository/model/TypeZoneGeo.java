package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table (name = "TYPE_ZONE_GEO")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeZoneGeo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String code;
    @Column
    private String libelle;
    @ManyToOne
    @JoinColumn ()
    private TypeZoneGeo parent;
}
