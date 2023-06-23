package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.mappers.AccountMapper;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountTokenRepository;
import com.coinverse.api.common.validators.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountTokenRepository accountTokenRepository;

    private final AccountIdRequestValidator accountIdRequestValidator;
    private final AccountStatusNameValidator accountStatusNameValidator;
    private final AccountUsernameRequestValidator accountUsernameRequestValidator;

    private final AccountTokenTypeNameRequestValidator accountTokenTypeNameRequestValidator;
    private final AccountTokenIdRequestValidator accountTokenIdRequestValidator;
    private final AccountTokenRequestValidator accountTokenRequestValidator;

    private final AccountMapper accountMapper;

    @Override
    public Optional<AccountResponse> getAccountById(@NotNull final Long id) {
        return accountRepository.findById(id).map(accountMapper::accountToAccountResponse);
    }

    @Override
    public Optional<AccountResponse> getAccountByUsername(@NotNull final String username) {
        return accountRepository.findByUsername(username).map(accountMapper::accountToAccountResponse);
    }

    @Transactional
    @Override
    public void addAccountLoginAttemptsById(@NotNull final Long accountId) {
        final Account account = accountIdRequestValidator.validate(accountId);
        account.setLoginAttempts(account.getLoginAttempts() + 1);
        account.setLastLoginAt(OffsetDateTime.now(ZoneOffset.UTC));
        accountRepository.save(account);
    }

    @Override
    public AccountResponse updateAccountById(@NotNull final Long id, @NotNull final AccountUpdateRequest accountUpdateRequest) {
        return null;
    }

    @Override
    public AccountResponse updateAccountByUsername(@NotNull final String username, @NotNull final AccountUpdateRequest accountUpdateRequest) {
        return null;
    }

    @Transactional
    @Override
    public void verifyAccount(@NotNull final Long accountId, @NotNull final Long accountTokeId) {
        final String verifiedAccountStatusName = AccountStatusEnum.VERIFIED.getName();

        final AccountStatus accountStatus = accountStatusNameValidator.validate(verifiedAccountStatusName);

        final AccountToken accountToken = accountTokenIdRequestValidator.validate(accountTokeId);

        final Account account = accountIdRequestValidator.validate(accountId);

        account.setStatus(accountStatus);
        account.setLastLoginAt(OffsetDateTime.now(ZoneOffset.UTC));
        account.setLoginAttempts(0);

        accountRepository.save(account);
        accountTokenRepository.delete(accountToken);
    }

    @Transactional
    @Override
    public void deleteAccountById(@NotNull final Long id) {
        final Account account = accountIdRequestValidator.validate(id);
        accountRepository.delete(account);
    }

    @Transactional
    @Override
    public void deleteAccountByUsername(@NotNull final String username) {
        final Account account = accountUsernameRequestValidator.validate(username);
        accountRepository.delete(account);
    }

    @Transactional
    @Override
    public void resetAccountLoginAttemptsById(@NotNull final Long accountId) {
        final Account account = accountIdRequestValidator.validate(accountId);

        account.setLastLoginAt(OffsetDateTime.now(ZoneOffset.UTC));
        account.setLoginAttempts(0);
        accountRepository.save(account);
    }

    @Override
    public Optional<AccountTokenResponse> getAccountTokenById(@NotNull final Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<AccountTokenResponse> getAccountTokenByAccountIdAndTokenTypeName(
            @NotNull final Long accountId,
            @NotNull final String tokenTypeName) {
        final AccountTokenType accountTokenType = accountTokenTypeNameRequestValidator.validate(tokenTypeName);

        final Long tokenTypeId = accountTokenType.getId();

        final Optional<AccountToken> accountTokenResponse =
                accountTokenRepository.findByAccountIdAndTypeId(accountId, tokenTypeId);

        if (accountTokenResponse.isEmpty()) {
            return Optional.empty();
        }

        final AccountToken accountToken = accountTokenResponse.get();

        return Optional.of(
                accountMapper
                        .accountTokenToAccountTokenResponse(accountToken)
        );
    }

    @Override
    public List<AccountTokenResponse> getAccountTokensByAccountId(@NotNull final Long id) {
        return null;
    }

    @Transactional
    @Override
    public void updateAccountTokenById(@NotNull final Long id,
                                              @NotNull final AccountTokenUpdateRequest accountTokenUpdateRequest) {
        final AccountToken accountToken = accountTokenIdRequestValidator.validate(id);

        accountToken.setKey(accountTokenUpdateRequest.getKey());
        accountToken.setExpiresAt(accountTokenUpdateRequest.getExpiresAt());
        accountTokenRepository.save(accountToken);
    }

    @Transactional
    @Override
    public void addAccountTokenByAccountId(@NotNull final Long id, @NotNull final AccountTokenRequest accountTokenRequest) {
        final Account account = accountIdRequestValidator.validate(id);
        final AccountToken accountToken = accountTokenRequestValidator.validate(accountTokenRequest);
        accountToken.setAccount(account);

        final Optional<AccountToken> existingAccountTokenResponse = accountTokenRepository
                .findByAccountIdAndTypeId(id, accountToken.getType().getId());

        if (existingAccountTokenResponse.isPresent()) {
            final AccountToken existingAccountToken = existingAccountTokenResponse.get();

            updateAccountTokenById(
                    existingAccountToken.getId(),
                    accountMapper.accountTokenRequestToAccountTokenUpdateRequest(accountTokenRequest)
            );

            return;
        }

        accountTokenRepository.save(accountToken);
    }

    @Override
    public void addAccountTokenUsageAttemptByTokenId(@NotNull final Long id) {
        AccountToken accountToken = accountTokenIdRequestValidator.validate(id);
        accountToken.setNumberOfUsageAttempts(accountToken.getNumberOfUsageAttempts() + 1);
        accountToken.setLastUsageAttemptAt(OffsetDateTime.now(ZoneOffset.UTC));

        accountTokenRepository.save(accountToken);
    }

    @Override
    public void deleteAccountTokenByAccountIdAndTokenTypeName(@NotNull final Long accountId,
                                                                          @NotNull final String tokenTypeName) {

    }
}
