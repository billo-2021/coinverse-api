package com.coinverse.api.features.account.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.common.services.UserAccountEventService;
import com.coinverse.api.features.account.validators.AccountValidator;
import com.coinverse.api.features.account.models.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final UserAccountService userAccountService;
    private final UserAccountEventService userAccountEventService;
    private final AccountValidator accountValidator;

    @Transactional
    @Override
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        final UserAccount userPrincipal = userAccountService.getCurrentUser();
        final Account account = accountValidator.validateChangePasswordRequest(userPrincipal.getUsername(),
                updatePasswordRequest);

        account.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));
        accountRepository.save(account);
    }

    @Override
    public PageResponse<UserAccountEventResponse> getAccountActivity(Pageable pageable) {
        final UserAccount userPrincipal = userAccountService.getCurrentUser();
        return userAccountEventService.getUserAccountEvents(userPrincipal.getUsername(), pageable);
    }
}
