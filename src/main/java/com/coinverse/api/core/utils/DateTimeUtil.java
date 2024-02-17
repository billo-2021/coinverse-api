package com.coinverse.api.core.utils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalUnit;

public class DateTimeUtil {
    public static OffsetDateTime getCurrentTimeStamp() {
        return OffsetDateTime.now();
    }

    public static boolean isBeforeNow(OffsetDateTime dateTime, long amount, TemporalUnit unit) {
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        return now.isBefore(dateTime.withOffsetSameInstant(ZoneOffset.UTC).plus(amount, unit));
    }
}
