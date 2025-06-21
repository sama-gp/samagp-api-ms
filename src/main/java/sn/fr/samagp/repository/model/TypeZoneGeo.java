package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "type_zone_geo") // Nom de table en minuscules pour PostgreSQL
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeZoneGeo implements Serializable {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "parent_code", referencedColumnName = "code")
    private TypeZoneGeo parent;
}
