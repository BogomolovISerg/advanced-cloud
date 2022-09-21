package com.geekbrains.coreservice.configs;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class PayPalConfiguration {

    @Value("${paypal.client-id}")
    private  String clientId;

    @Value("${paypal.client-secret}")
    private  String secret;

    private PayPalEnvironment environment;

    @PostConstruct
    private void iniy(){
        this.environment = new PayPalEnvironment.Sandbox(clientId, secret);
    }

    @Bean
    public PayPalHttpClient payPalHttpClient(){
        return new PayPalHttpClient(environment);
    }
}
