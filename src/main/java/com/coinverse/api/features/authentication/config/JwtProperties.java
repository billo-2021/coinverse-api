package com.coinverse.api.features.authentication.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("security.jwt")
public record JwtProperties(
        String secretKey){

}
