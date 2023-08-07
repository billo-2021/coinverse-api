package com.coinverse.api.common.security.services;

import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.models.UserAccountEventTypeEnum;
import com.coinverse.api.common.security.exceptions.*;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.services.UserAccountEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final UserAccountEventService userAccountEventService;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse authenticate(String username, String password) {
        AccountResponse accountResponse = getUserAccount(username);

        if (!passwordEncoder.matches(password, accountResponse.getPassword())) {
            accountService.addAccountLoginAttemptsById(accountResponse.getId());
            userAccountEventService.addEvent(accountResponse.getUsername(), UserAccountEventTypeEnum.LOGIN_ATTEMPT_FAILURE);

            throw new InvalidCredentialsException();
        }

        try {
            final UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            final Authentication authentication = authenticationManager.
                    authenticate(usernamePasswordAuthToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            accountService.resetAccountLoginAttemptsById(accountResponse.getId());
            userAccountEventService.addEvent(UserAccountEventTypeEnum.LOGIN_ATTEMPT_SUCCESS);

            return accountResponse;
        } catch (AuthenticationException ex) {
            return handleAuthenticationException(ex, accountResponse);
        }
    }

    private AccountResponse getUserAccount(String username) {
        return accountService.getAccountByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);
    }

    private AccountResponse handleAuthenticationException(AuthenticationException ex, AccountResponse accountResponse) {
        if (!(ex instanceof DisabledException && accountResponse.getIsEnabled())) {
            throw translateException(ex, true, accountResponse.getIsEnabled());
        }

        userAccountEventService.addEvent(accountResponse.getUsername(), UserAccountEventTypeEnum.LOGIN_ATTEMPT_SUCCESS);

        return accountResponse;
    }

    private ApiAuthenticationException translateException(AuthenticationException authEx, boolean passwordsMatch, boolean isAccountEnabled) {
        if (passwordsMatch && authEx instanceof LockedException) {
            return new AccountLockedException();
        }

        if (passwordsMatch && authEx instanceof DisabledException && isAccountEnabled) {
            return new VerificationRequiredException();
        }

        if (passwordsMatch && authEx instanceof DisabledException) {
            return new AccountDisabledException();
        }

        return new ApiAuthenticationException(authEx);
    }
}
