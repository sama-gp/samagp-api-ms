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
    private ItinerraireId id = new ItinerraireId();

    @MapsId("departId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IT_ZN_DEPART", referencedColumnName = "id")
    private ZoneGeo depart;

    @MapsId("arriveeId")
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "IT_ZN_ARRIVEE", referencedColumnName = "id")
    private ZoneGeo arrivee;

    // Ajoutez cette méthode pour initialiser correctement l'ID
    @PrePersist
    public void initId() {
        if (this.id == null) {
            this.id = new ItinerraireId();
        }
        if (this.depart != null) {
            this.id.setDepartId(this.depart.getId());
        }
        if (this.arrivee != null) {
            this.id.setArriveeId(this.arrivee.getId());
        }
    }

}
