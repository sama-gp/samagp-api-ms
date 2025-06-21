package sn.fr.samagp.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItinerraireId implements Serializable {
    @Column(name = "IT_ZN_DEPART")
    private Long departId;

    @Column(name = "IT_ZN_ARRIVEE")
    private Long arriveeId;

    // Implémentez equals() et hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItinerraireId that = (ItinerraireId) o;
        return Objects.equals(departId, that.departId) &&
                Objects.equals(arriveeId, that.arriveeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departId, arriveeId);
    }
}