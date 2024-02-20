package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.models.Request;
import com.crni99.qrcodegenerator.models.Response;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PaymentIntentController {

    @PostMapping("/create-payment-intent")
    public Response createPaymentIntent(@RequestBody Request request) throws StripeException {
        // Multiplica el BigDecimal por 100 y conviértelo a long
        long amountInCents = request.getAmount().multiply(BigDecimal.valueOf(100)).longValue();

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .putMetadata("productName", request.getProductName())
                .setCurrency("eur")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams
                                .AutomaticPaymentMethods
                                .builder()
                                .setEnabled(true)
                                .build()
                )
                .build();

        PaymentIntent intent = PaymentIntent.create(params);
        return new Response(intent.getId(), intent.getClientSecret());
    }


}
