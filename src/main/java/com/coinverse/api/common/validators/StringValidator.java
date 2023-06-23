package com.coinverse.api.common.validators;

import jakarta.validation.Payload;

public @interface StringValidator {
    String[] acceptedValues();

    String message() default "value is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
