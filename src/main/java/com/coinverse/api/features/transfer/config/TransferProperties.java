package com.coinverse.api.features.transfer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("transfer.properties")
public record TransferProperties(
        String systemUsername,
        String systemPassword
) {
}
