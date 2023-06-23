package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

public interface TokenComparator<T> {
    boolean matches(@NotNull T other);
}
