package sn.fr.samagp.repository.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record StripePaymentRequest(
        @NotNull UUID abonnementClientId,
        @NotNull String stripeToken,
        String email
) {}