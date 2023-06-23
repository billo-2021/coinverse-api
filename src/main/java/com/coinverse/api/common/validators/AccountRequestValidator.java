package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.entities.Role;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.AccountRequest;
import com.coinverse.api.common.models.AccountStatusEnum;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountStatusRepository;
import com.coinverse.api.common.repositories.RoleRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountRequestValidator implements RequestValidator<AccountRequest, Account> {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AccountStatusRepository accountStatusRepository;
    private final AccountRepository accountRepository;

    @Override
    public Account validate(@NotNull final AccountRequest accountRequest) throws InvalidRequestException, MappingException {
        accountRepository.findByUsername(accountRequest.getUsername())
                .ifPresent((account -> {
                    throw new ValidationException("User account with username '" +
                            account.getUsername() + "' already exists", "account.username");
                }));

        final Set<String> accountRolesRequest = accountRequest.getRoles();

        final Set<Role> roles = accountRolesRequest
                .stream()
                .map(roleName -> {
                    return roleRepository.findByAuthority(roleName)
                            .orElseThrow(() ->
                                    new ValidationException("Invalid role '" + roleName + "'", "account.roles"));

                }).collect(Collectors.toSet());

        final String pendingVerificationStatusName = AccountStatusEnum.PENDING_VERIFICATION.getName();

        final AccountStatus accountStatus = accountStatusRepository
                .findByName(pendingVerificationStatusName)
                .orElseThrow(() ->
                        new MappingException("Invalid account status '" + pendingVerificationStatusName + "'")
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
