package com.coinverse.api.common.validators;

import io.jsonwebtoken.lang.Assert;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class EnumValidatorRegisterString implements ConstraintValidator<EnumValidator, String> {
    private static BiPredicate<? super EnumValidatorComparator<?>, String> defaultComparison = (currentEnumValue, testValue) -> {
        return currentEnumValue.toString().equals(testValue);
    };

    public static void setDefaultComparison(BiPredicate<? super EnumValidatorComparator<?>, String> defaultComparison) {
        Assert.notNull(defaultComparison, "Default comparison can't be null");
        EnumValidatorRegisterString.defaultComparison = defaultComparison;
    }

    private Class<? extends EnumValidatorComparator<?>> clazz;
    private EnumValidatorComparator<?>[] valuesArr;

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        clazz = constraintAnnotation.target();
        valuesArr = clazz.getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isEmpty() || value.trim().isEmpty()) {
            return false;
        }

        boolean present;
        if (EnumValidatorComparator.class.isAssignableFrom(clazz)) {
            present = Stream.of(valuesArr).anyMatch((item) -> item.test(value));
        } else {
            present = Stream.of(valuesArr).anyMatch((t) -> defaultComparison.test(t, value));
        }

        if (!present) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    String.format(
                            "'%s' is not one of the one of allowed values: %s".formatted(
                                    value,
                                    Stream.of(valuesArr).map((Object object) -> {
                                        return object.toString();
                                    }).toList().toString()
                            )
                    )
            ).addConstraintViolation();
        }

        return present;
    }
}
