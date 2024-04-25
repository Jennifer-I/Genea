package com.jennifer.config;

import feign.Feign;
import feign.RequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfiguration {

    @Value("${PayStack_Secret.key}")
    private String payStackSecretKey;




    @Bean
        public PayStackClient paystackClient() {
            return Feign.builder()
                    .encoder(new GsonEncoder())
                    .decoder(new GsonDecoder())
                    .requestInterceptor(authInterceptor())
                    .target(PayStackClient.class, "https://api.paystack.co");
        }
    @Bean
    public RequestInterceptor authInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + payStackSecretKey);
        };
    }
}


