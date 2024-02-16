package com.crni99.qrcodegenerator.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class configuracioStripe {

    @Value("${stripe.apikey}")
    private String secretKey;

    @PostConstruct
    public void initSecretKey(){
        Stripe.apiKey = secretKey;
    }
}
