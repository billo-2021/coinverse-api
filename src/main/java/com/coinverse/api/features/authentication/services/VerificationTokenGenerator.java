package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.services.StringTokenGenerator;
import com.coinverse.api.common.utils.TokenUtil;
import com.coinverse.api.core.utils.RandomUtil;
import com.coinverse.api.common.models.StringToken;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Component
public class VerificationTokenGenerator implements StringTokenGenerator {
    private static final int DEFAULT_KEY_LENGTH = 8;
    private static final Duration DEFAULT_DURATION = Duration.of(5, ChronoUnit.MINUTES);

    private final int keyLength;
    private final Duration duration;

    VerificationTokenGenerator() {
        this(DEFAULT_KEY_LENGTH, DEFAULT_DURATION);
    }

    VerificationTokenGenerator(int keyLength, Duration duration) {
        this.keyLength = keyLength;
        this.duration = duration;
    }

    @Override
    public StringToken generate() {
        final String key = RandomUtil.nextIntKey(keyLength).toString();

        return new StringToken(key, TokenUtil.getTokenExpirationFromNow(duration));
    }
}
