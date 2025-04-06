package sn.fr.samagp.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItinerraireId implements Serializable {
    @Column(name = "IT_ZN_DEPART")
    private Long departId;
    @Column(name = "IT_ZN_ARRIVEE")
    private Long arriveeId;
}
