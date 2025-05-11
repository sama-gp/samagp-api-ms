package sn.fr.samagp.repository.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CLIENTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private String firstName;
    private String lastName;
    @Email(message = "The email must be valid")
    @NotBlank(message = "The email is required")
    private String email;

    @NotBlank(message = "The password is required")
    private String password;

    @Enumerated(EnumType.STRING)
    private Profile profile;

    @NotBlank(message = "The phone number is required")
    private String phone;

    @NotBlank(message = "The address is required")
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.DETACH)
    private List<Annonce> annonces = new ArrayList<>();


}
