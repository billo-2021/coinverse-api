package com.coinverse.api.common.validators;

import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import jakarta.validation.constraints.NotNull;

public interface RequestValidator<TRequest, TResult> {
    TResult validate(@NotNull final TRequest request) throws InvalidRequestException, MappingException;
}
