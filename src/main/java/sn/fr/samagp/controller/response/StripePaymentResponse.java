package sn.fr.samagp.controller.response;


import java.util.UUID;

public record StripePaymentResponse(
        String paymentIntentId,
        String clientSecret,
        String status,
        String message
) {}