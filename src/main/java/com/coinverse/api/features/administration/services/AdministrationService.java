package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.administration.models.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;

public interface AdministrationService {
    PageResponse<UserResponse> getUsers(@NotNull final PageRequest pageRequest);
    void disableUserAccount(@NotNull final String username);
    void enableUserAccount(@NotNull final String username);
}
