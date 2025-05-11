package sn.fr.samagp.repository.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.fr.samagp.repository.model.Profile;

import java.util.List;
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

        @NotBlank(message = "The phone number is required")
        private String phone;

        @NotBlank(message = "The address is required")
        private String address;

        @NotNull(message = "Profile is required")
        private Profile profile;

        private List<Long> annoncesIds;
        private List<Long> favorisIds;
}

