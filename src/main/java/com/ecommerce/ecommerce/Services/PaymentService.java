package com.ecommerce.ecommerce.Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.ecommerce.ecommerce.Models.Token;
import com.ecommerce.ecommerce.common.payload.request.PaymentRequest;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class PaymentService {

    private String secretKey = "";
    @Value("${spring.profiles.active}")
    private String envType;
    @Autowired
    private PaymentApiKeyService paymentApiKeyService;
    @PostConstruct
    public void init() {
        if(envType.equals("dev")){
            Stripe.apiKey = paymentApiKeyService.findDevKey();
        }else {
            Stripe.apiKey = paymentApiKeyService.findProdKey();
        }
        secretKey = Stripe.apiKey;
    }
    public String charge(int amount,String currency,String tokenId) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", currency);
        Token token = new Token();
        token.setId(tokenId);
        chargeParams.put("source", token.getId());
        System.out.println("DDDDDDDDDDDDDDDDD");
        System.out.println(amount);
        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }
    public PaymentIntent paymentIntent(PaymentRequest paymentRequest) throws StripeException {
        Stripe.apiKey = secretKey;
        List<String> paymentMethodTypes = new ArrayList();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequest.getAmount());
        params.put("currency", paymentRequest.getCurrency());
        params.put("description", paymentRequest.getDescription());
        params.put("payment_method_types", paymentMethodTypes);
        return PaymentIntent.create(params);
    }

    public PaymentIntent confirm(String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");
        paymentIntent.confirm(params);
        return paymentIntent;
    }

    public PaymentIntent cancel(String id) throws StripeException {
        Stripe.apiKey = secretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(id);
        paymentIntent.cancel();
        return paymentIntent;
    }


    public String processPayment(int amount,String currency,String tokenId) throws StripeException {
        String chargeId = charge(amount,currency,tokenId);
        return chargeId;
    }
}
