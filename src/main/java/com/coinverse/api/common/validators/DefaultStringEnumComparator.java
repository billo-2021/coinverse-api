package com.coinverse.api.common.validators;

import jakarta.validation.constraints.NotNull;

public interface DefaultStringEnumComparator extends EnumValidatorComparator<String> {
    String getCode();
    @Override
    default boolean test(@NotNull Object other) {
        if (!(other instanceof String otherString)) {
            return false;
        }

        return getCode().equals(otherString.toLowerCase());
    }
}
