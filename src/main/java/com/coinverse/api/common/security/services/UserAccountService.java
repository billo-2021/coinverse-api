package com.coinverse.api.common.security.services;

import com.coinverse.api.common.security.models.UserAccount;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserAccountService extends UserDetailsService {
    Optional<UserAccount> getUserAccountById(final @NotNull Long id);
}
