package com.coinverse.api.features.messaging.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("messaging")
public record MessagingProperties(
        String emailSource,
        String smsSource
) {
}
