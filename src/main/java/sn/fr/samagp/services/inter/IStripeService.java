package sn.fr.samagp.services.inter;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import sn.fr.samagp.repository.model.AbonnementClient;

public interface IStripeService {

    PaymentIntent createPaymentIntent(AbonnementClient abonnement, String stripeToken) throws StripeException;
    PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException;
    PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException;
}
