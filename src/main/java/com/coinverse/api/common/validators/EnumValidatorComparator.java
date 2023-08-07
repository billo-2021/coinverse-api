package com.coinverse.api.common.validators;

import jakarta.validation.constraints.NotNull;

public interface EnumValidatorComparator <T> {
    boolean test(@NotNull Object other);
}
