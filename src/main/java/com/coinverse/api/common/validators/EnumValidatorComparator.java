package com.coinverse.api.common.validators;

import jakarta.validation.constraints.NotNull;

public interface EnumValidatorComparator <T> {
    public boolean test(@NotNull T other);
}
