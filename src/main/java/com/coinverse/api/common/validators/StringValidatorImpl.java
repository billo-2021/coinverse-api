package com.coinverse.api.common.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class StringValidatorImpl implements ConstraintValidator<StringValidator, String> {
    private List<String> valueList = null;

    @Override
    public void initialize(StringValidator constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        valueList = new ArrayList<>();

        for(String val : constraintAnnotation.acceptedValues()) {
            valueList.add(val.toUpperCase());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return valueList.contains(value.toUpperCase());
    }
}
