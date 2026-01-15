package sn.fr.samagp.services.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sn.fr.samagp.configuration.paiement.StripeProperties;
import sn.fr.samagp.repository.model.AbonnementClient;
import sn.fr.samagp.repository.model.Devise;
import sn.fr.samagp.services.inter.IStripeService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements IStripeService {

    private final StripeProperties stripeProperties;

    @Override
    public PaymentIntent createPaymentIntent(AbonnementClient abonnement, String stripeToken) throws StripeException {
        Stripe.apiKey = stripeProperties.getSecretKey();

        BigDecimal montant = abonnement.getPlan().getPrix();
        Long amountInCents = convertToCents(montant);
        String currency = getCurrencyCode(abonnement.getPlan().getDevise());

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amountInCents);
        params.put("currency", currency);

        // CORRECTION : Utiliser payment_method_data avec type "card" en chaîne
        Map<String, Object> paymentMethodData = new HashMap<>();
        paymentMethodData.put("type", "card");

        Map<String, Object> cardData = new HashMap<>();
        cardData.put("token", stripeToken);
        paymentMethodData.put("card", cardData);

        params.put("payment_method_data", paymentMethodData);
        params.put("confirm", true);
        params.put("confirmation_method", "automatic");
        params.put("return_url", "http://localhost:4200/payment/success");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("abonnement_client_id", abonnement.getId().toString());
        metadata.put("client_id", abonnement.getClient().getId().toString());
        metadata.put("plan_code", abonnement.getPlan().getCode());
        params.put("metadata", metadata);

        log.info("Création PaymentIntent Stripe avec token: {}", stripeToken.substring(0, 10) + "...");
        return PaymentIntent.create(params);
    }

    @Override
    public PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeProperties.getSecretKey();
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return paymentIntent.confirm();
    }

    @Override
    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeProperties.getSecretKey();
        return PaymentIntent.retrieve(paymentIntentId);
    }

    private Long convertToCents(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(100)).longValue();
    }

    private String getCurrencyCode(Devise devise) {
        return switch (devise) {
            case XOF -> "xof";
            case EUR -> "eur";
            case USD -> "usd";
            default -> "xof";
        };
    }
}