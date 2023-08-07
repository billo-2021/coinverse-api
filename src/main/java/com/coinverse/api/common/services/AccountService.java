package com.coinverse.api.common.services;

import com.coinverse.api.common.models.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {
    Optional<AccountResponse> getAccountById(Long id);
    Optional<AccountResponse> getAccountByUsername(String username);
    void addAccountLoginAttemptsById(Long accountId);
    AccountResponse updateAccountById(Long id, AccountUpdateRequest accountUpdateRequest);
    AccountResponse updateAccountByUsername(String username, AccountUpdateRequest accountUpdateRequest);
    void verifyAccount(Long accountId, Long accountTokenId);
    void deleteAccountById(Long id);
    void deleteAccountByUsername(String username);
    void resetAccountLoginAttemptsById(Long accountId);
    Optional<AccountTokenResponse> getAccountTokenById(Long id);
    Optional<AccountTokenResponse> getAccountTokenByAccountIdAndTokenTypeCode(
            Long accountId, String tokenTypeCode);
    List<AccountTokenResponse> getAccountTokensByAccountId(Long id);

    void updateAccountTokenById(Long id,
                                       AccountTokenUpdateRequest accountTokenUpdateRequest);
    void addAccountTokenByAccountId(Long id, AccountTokenRequest accountTokenRequest);
    void addAccountTokenUsageAttemptByTokenId(Long id);

    void deleteAccountTokenByAccountIdAndTokenTypeName(Long accountId, String tokenTypeName);
}
