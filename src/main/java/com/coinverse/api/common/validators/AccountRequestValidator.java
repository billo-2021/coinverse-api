package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.entities.Role;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.AccountRequest;
import com.coinverse.api.common.models.AccountStatusEnum;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountStatusRepository;
import com.coinverse.api.common.repositories.RoleRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountRequestValidator {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final AccountRepository accountRepository;

    public Account validateId(Long id) throws InvalidRequestException, MappingException {
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        ErrorMessageUtils.getValidationException("accountId", String.valueOf(id))
                );
    }

    public Account validateUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);
    }

    public Account validateAccountRequest(AccountRequest accountRequest) throws InvalidRequestException, MappingException {
        accountRepository.findByUsernameIgnoreCase(accountRequest.getUsername())
                .ifPresent((account -> {
                    throw ErrorMessageUtils.getAlreadyExistValidationException("User account", "account.username", account.getUsername());
                }));

        final Set<String> accountRolesRequest = accountRequest.getRoles();

        final Set<Role> roles = accountRolesRequest
                .stream()
                .map(roleName -> roleRepository.findByAuthorityIgnoreCase(roleName)
                        .orElseThrow(() ->
                                ErrorMessageUtils.getValidationException("account.roles", roleName)
                        )).collect(Collectors.toSet());

        final String pendingVerificationStatusCode = AccountStatusEnum.PENDING_VERIFICATION.getCode();

        final AccountStatus accountStatus = accountStatusRepository
                .findByCodeIgnoreCase(pendingVerificationStatusCode)
                .orElseThrow(() -> ErrorMessageUtils.getMappingException("account.status", pendingVerificationStatusCode)
                );

        return Account.builder()
                .username(accountRequest.getUsername())
                .password(passwordEncoder.encode(accountRequest.getPassword()))
                .roles(roles)
                .status(accountStatus)
                .isEnabled(true)
                .loginAttempts(0)
                .build();
    }
}
