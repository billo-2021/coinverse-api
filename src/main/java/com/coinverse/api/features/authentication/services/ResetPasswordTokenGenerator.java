package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.StringToken;
import com.coinverse.api.common.services.StringTokenGenerator;
import com.coinverse.api.common.utils.TokenUtil;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Component
public class ResetPasswordTokenGenerator implements StringTokenGenerator {
    private static final Duration DEFAULT_DURATION = Duration.of(5, ChronoUnit.MINUTES);
    private final Duration duration;

    ResetPasswordTokenGenerator() {
        this(DEFAULT_DURATION);
    }

    ResetPasswordTokenGenerator(final Duration duration) {
        this.duration = duration;
    }

    @Override
    public StringToken generate() {
        final String key = UUID.randomUUID().toString();

        return new StringToken(key, TokenUtil.getTokenExpirationFromNow(duration));
    }
}
