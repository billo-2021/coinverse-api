package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public class StringToken extends Token<String> {
    public StringToken(@NotNull final String key, @NotNull final OffsetDateTime expiresAt) {
        super(key, expiresAt);
    }

    @Override
    public boolean matches(@NotNull final String other) {
        return key.equals(other);
    }
}
