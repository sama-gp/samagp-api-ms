package sn.fr.samagp.repository.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.fr.samagp.repository.model.Adresse;
import sn.fr.samagp.repository.model.Profile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

        private UUID id;
        @NotBlank(message = "First name is required")
        private String firstName;

        @NotBlank(message = "Last name is required")
        private String lastName;

        @Email(message = "The email must be valid")
        @NotBlank(message = "The email is required")
        private String email;

        @NotBlank(message = "The password is required")
        private String password;

        private List<String> phone;

        private List<Adresse> address;

        @NotNull(message = "Profile is required")
        private Profile profile;

        private LocalDateTime createdAt;

        private List<Long> annoncesIds;
        private List<Long> favorisIds;
        private String keycloakId;

        // Champs pour le suivi
        private Set<UUID> followingIds;
        private Set<UUID> followerIds;
        private int followingCount;
        private int followersCount;
        private boolean following; ;
}

