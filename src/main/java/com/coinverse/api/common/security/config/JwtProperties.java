package com.coinverse.api.common.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("security.jwt")
public record JwtProperties(
        String secretKey){

}
