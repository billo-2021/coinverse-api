package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.administration.models.UserResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {
    private final UserManager userManager;
    private final UserAccountManager userAccountManager;

    @Override
    public PageResponse<UserResponse> getUsers(@NotNull final PageRequest pageRequest) {
        return userManager.getUsers(pageRequest);
    }

    @Override
    public void disableUserAccount(@NotNull final String username) {
        userAccountManager.disableAccount(username);
    }

    @Override
    public void enableUserAccount(@NotNull final String username) {
        userAccountManager.enableUserAccount(username);
    }
}
