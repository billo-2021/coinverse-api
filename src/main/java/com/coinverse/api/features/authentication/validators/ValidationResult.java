package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.exceptions.ApiException;

import java.util.*;

public class ValidationResult<T extends ApiException> {
    private List<T> errors;
    private ValidationResultData data;

    public ValidationResult() {
        errors = new ArrayList<>();
        data = new ValidationResultData();
    }

    public void setErrors(List<T> errors) {
        this.errors = errors;
    }

    public List<T> getErrors() {
        return errors;
    }

    public <C> Optional<C> getError(Class<C> cls) {
        if (getIsValid()) {
            throw new NoSuchElementException();
        }

        var errorResult = errors.stream()
                .filter((error) -> canCast(error, cls))
                .findFirst();

        if (errorResult.isEmpty()) {
            return Optional.empty();
        }

        var error = cls.cast(errorResult.get());

        return Optional.of(error);
    }

    private static <C> boolean canCast(Object o, Class<C> clazz) {
        try {
            clazz.cast(o);
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public void addError(T error) {
        this.errors.add(error);
    }

    public boolean getIsValid() {
        return errors == null || errors.size() > 0;
    }

    public ValidationResultData getData() {
        if (!getIsValid()) {
            throw new NoSuchElementException();
        }

        return data;
    }

    public void setData(ValidationResultData data) {
        this.data = data;
    }
}
