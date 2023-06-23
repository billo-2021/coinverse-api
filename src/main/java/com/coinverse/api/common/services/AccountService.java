package com.coinverse.api.common.services;

import com.coinverse.api.common.models.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {
    Optional<AccountResponse> getAccountById(@NotNull final Long id);
    Optional<AccountResponse> getAccountByUsername(@NotNull final String username);
    void addAccountLoginAttemptsById(@NotNull final Long accountId);
    AccountResponse updateAccountById(@NotNull final Long id, @NotNull final AccountUpdateRequest accountUpdateRequest);
    AccountResponse updateAccountByUsername(@NotNull final String username, @NotNull final AccountUpdateRequest accountUpdateRequest);
    void verifyAccount(@NotNull final Long accountId, @NotNull final Long accountTokenId);
    void deleteAccountById(@NotNull final Long id);
    void deleteAccountByUsername(@NotNull final String username);
    void resetAccountLoginAttemptsById(@NotNull final Long accountId);
    Optional<AccountTokenResponse> getAccountTokenById(@NotNull final Long id);
    Optional<AccountTokenResponse> getAccountTokenByAccountIdAndTokenTypeName(
            @NotNull final Long accountId, @NotNull final String tokenTypeName);
    List<AccountTokenResponse> getAccountTokensByAccountId(@NotNull final Long id);

    void updateAccountTokenById(@NotNull final Long id,
                                       @NotNull final AccountTokenUpdateRequest accountTokenUpdateRequest);
    void addAccountTokenByAccountId(@NotNull final Long id, @NotNull final AccountTokenRequest accountTokenRequest);
    void addAccountTokenUsageAttemptByTokenId(@NotNull final Long id);

    void deleteAccountTokenByAccountIdAndTokenTypeName(@NotNull final Long accountId, final @NotNull String tokenTypeName);
}
