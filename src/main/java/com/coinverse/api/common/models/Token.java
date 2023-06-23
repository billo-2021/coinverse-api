package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public abstract class Token<T> implements TokenComparator<T>  {
    protected T key;
    protected OffsetDateTime expiresAt;

    public Token(@NotNull final T key, @NotNull final OffsetDateTime expiresAt) {
        this.key = key;
        this.expiresAt = expiresAt;
    }

    public T getKey() {
        return key;
    }

    public OffsetDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean hasExpired() {
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        return now.isAfter(expiresAt);
    }

    public boolean hasExpired(@NotNull final OffsetDateTime futureDate) {
        return futureDate.isBefore(expiresAt);
    }
}
