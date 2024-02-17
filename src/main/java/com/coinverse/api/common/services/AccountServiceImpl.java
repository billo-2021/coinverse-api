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

    private final AccountRequestValidator accountRequestValidator;
    private final AccountStatusNameValidator accountStatusNameValidator;
    private final AccountUsernameRequestValidator accountUsernameRequestValidator;

    private final AccountTokenTypeCodeRequestValidator accountTokenTypeCodeRequestValidator;
    private final AccountTokenIdRequestValidator accountTokenIdRequestValidator;
    private final AccountTokenRequestValidator accountTokenRequestValidator;

    private final AccountMapper accountMapper;

    @Override
    public Optional<AccountResponse> getAccountById(Long id) {
        return accountRepository.findById(id).map(accountMapper::accountToAccountResponse);
    }

    @Override
    public Optional<AccountResponse> getAccountByUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username).map(accountMapper::accountToAccountResponse);
    }

    @Transactional
    @Override
    public void addAccountLoginAttemptsById(Long accountId) {
        final Account account = accountRequestValidator.validateId(accountId);
        account.setLoginAttempts(account.getLoginAttempts() + 1);
        account.setLastLoginAt(OffsetDateTime.now(ZoneOffset.UTC));
        accountRepository.save(account);
    }

    @Override
    public AccountResponse updateAccountById(Long id, final AccountUpdateRequest accountUpdateRequest) {
        return null;
    }

    @Override
    public AccountResponse updateAccountByUsername(String username, final AccountUpdateRequest accountUpdateRequest) {
        return null;
    }

    @Transactional
    @Override
    public void verifyAccount(Long accountId, Long accountTokeId) {
        final String verifiedAccountStatusName = AccountStatusEnum.VERIFIED.getName();
        final AccountStatus accountStatus = accountStatusNameValidator.validate(verifiedAccountStatusName);
        final AccountToken accountToken = accountTokenIdRequestValidator.validate(accountTokeId);
        final Account account = accountRequestValidator.validateId(accountId);

        account.setStatus(accountStatus);
        account.setLastLoginAt(OffsetDateTime.now(ZoneOffset.UTC));
        account.setLoginAttempts(0);

        accountRepository.saveAndFlush(account);
        accountTokenRepository.delete(accountToken);
    }

    @Transactional
    @Override
    public void deleteAccountById(Long id) {
        final Account account = accountRequestValidator.validateId(id);
        accountRepository.delete(account);
    }

    @Transactional
    @Override
    public void deleteAccountByUsername(String username) {
        final Account account = accountUsernameRequestValidator.validate(username);
        accountRepository.delete(account);
    }

    @Transactional
    @Override
    public void resetAccountLoginAttemptsById(Long accountId) {
        final Account account = accountRequestValidator.validateId(accountId);

        account.setLastLoginAt(OffsetDateTime.now(ZoneOffset.UTC));
        account.setLoginAttempts(0);
        accountRepository.save(account);
    }

    @Override
    public Optional<AccountTokenResponse> getAccountTokenById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<AccountTokenResponse> getAccountTokenByAccountIdAndTokenTypeCode(
            @NotNull final Long accountId,
            @NotNull final String tokenTypeCode) {
        final AccountTokenType accountTokenType = accountTokenTypeCodeRequestValidator.validate(tokenTypeCode);

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
    public List<AccountTokenResponse> getAccountTokensByAccountId(Long id) {
        return null;
    }

    @Transactional
    @Override
    public void updateAccountTokenById(Long id,
                                              AccountTokenUpdateRequest accountTokenUpdateRequest) {
        final AccountToken accountToken = accountTokenIdRequestValidator.validate(id);

        accountToken.setKey(accountTokenUpdateRequest.getKey());
        accountToken.setExpiresAt(accountTokenUpdateRequest.getExpiresAt());
        accountTokenRepository.save(accountToken);
    }

    @Transactional
    @Override
    public void addAccountTokenByAccountId(Long id, AccountTokenRequest accountTokenRequest) {
        final Account account = accountRequestValidator.validateId(id);
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
    public void addAccountTokenUsageAttemptByTokenId(Long id) {
        AccountToken accountToken = accountTokenIdRequestValidator.validate(id);
        accountToken.setNumberOfUsageAttempts(accountToken.getNumberOfUsageAttempts() + 1);
        accountToken.setLastUsageAttemptAt(OffsetDateTime.now(ZoneOffset.UTC));

        accountTokenRepository.save(accountToken);
    }

    @Override
    public void deleteAccountTokenByAccountIdAndTokenTypeName(Long accountId,
                                                                          String tokenTypeName) {

    }
}
