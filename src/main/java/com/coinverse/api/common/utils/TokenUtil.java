package com.coinverse.api.common.utils;

import com.coinverse.api.core.utils.DateUtil;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TokenUtil {
    public static OffsetDateTime getTokenExpirationFromNow(Duration duration) {
        final Instant expiresAt = DateUtil.getInstantFromNow(duration);

        return OffsetDateTime.ofInstant(expiresAt, ZoneOffset.UTC);
    }
}
