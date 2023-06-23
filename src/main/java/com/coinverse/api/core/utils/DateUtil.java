package com.coinverse.api.core.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static Instant getInstantFromNow(final Duration duration) {
        return Instant.now().plus(duration);
    }

    public static Instant getInstantFromNow(int amount, final ChronoUnit unit) {
        return Instant.now().plus(amount, unit);
    }
}
