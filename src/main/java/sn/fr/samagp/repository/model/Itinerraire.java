package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Table (name ="ITINERAIRES")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Itinerraire implements Serializable {

    @EmbeddedId
    private ItinerraireId id;

    @MapsId("departId")
    @ManyToOne
    @JoinColumn (name = "IT_ZN-DEPART", referencedColumnName = "id")
    private ZoneGeo depart;

    @MapsId("arriveeId")
    @ManyToOne
    @JoinColumn (name = "IT_ZN-ARRIVEE", referencedColumnName = "id")
    private ZoneGeo arrivee;

}
