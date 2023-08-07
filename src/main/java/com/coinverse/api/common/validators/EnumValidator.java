package com.coinverse.api.common.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  {EnumValidatorRegisterString.class})
@ReportAsSingleViolation
public @interface EnumValidator {
    String message() default "value is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String detailMessage() default "";

    Class<? extends EnumValidatorComparator<?>> target();
}
