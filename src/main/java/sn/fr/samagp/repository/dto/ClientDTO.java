package sn.fr.samagp.repository.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sn.fr.samagp.repository.model.Profile;

import java.util.List;

public record ClientDTO(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Email(message = "The email must be valid")
        @NotBlank(message = "The email is required")
        String email,

        @NotBlank(message = "The password is required")
        String password,

        @NotBlank(message = "The phone number is required")
        String phone,

        @NotBlank(message = "The address is required")
        String address,

        @NotNull(message = "Profile is required")
        Profile profile,
        List<Long> annoncesIds,
        List<Long> favorisIds
) {

}


