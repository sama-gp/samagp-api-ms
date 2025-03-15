package sn.fr.samagp.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
@Entity (name = "TYPE_ZONE_GEOGRAPHYQUE")
public class TypeZoneGeo  implements Serializable {

    @Column(name = "TZ_CODE")
    @Id
    private String code;
    @ManyToOne
    @JoinColumn (name = "TZ_PARENT_CODE")
    private TypeZoneGeo parent;

}
