package sn.fr.samagp.repository.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Itineraire {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank(message = "the start Itinerary is required")
    private ZoneGeo start;
    @NotBlank(message = "the start Itinerary is required")
    private ZoneGeo arrived;
    private String description;
    @ManyToMany(mappedBy = "favoris")
    private List<Client> clients = new ArrayList<>();
}
