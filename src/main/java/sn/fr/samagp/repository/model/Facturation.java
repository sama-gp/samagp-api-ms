package sn.fr.samagp.repository.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "FACTURATIONS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Facturation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
