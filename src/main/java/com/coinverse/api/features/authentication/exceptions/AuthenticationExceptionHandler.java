package com.coinverse.api.features.authentication.exceptions;

import com.coinverse.api.common.errors.ErrorResponse;
import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.models.UserAccountEventRequest;
import com.coinverse.api.common.models.UserAccountEventTypeEnum;
import com.coinverse.api.common.security.exceptions.AccountDisabledException;
import com.coinverse.api.common.security.exceptions.AccountLockedException;
import com.coinverse.api.common.security.exceptions.ApiAuthenticationException;
import com.coinverse.api.common.security.exceptions.VerificationRequiredException;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.services.UserAccountEventService;
import com.coinverse.api.common.utils.ExceptionUtil;
import com.coinverse.api.features.authentication.models.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationExceptionHandler {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    private final UserAccountEventService userAccountEventService;

    @ExceptionHandler(LoginAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            final LoginAuthenticationException loginAuthEx) {
        final LoginRequest loginRequest = loginAuthEx.getRequest();
        final String password = loginRequest.getPassword();

        final AccountResponse accountResponse = loginAuthEx.getAccount();
        final Boolean isAccountEnabled = accountResponse.getIsEnabled();

        final AuthenticationException authEx = loginAuthEx.getException();

        final boolean passwordsMatch = passwordEncoder
                .matches(password, accountResponse.getPassword());

        accountService.addAccountLoginAttemptsById(accountResponse.getId());

        UserAccountEventTypeEnum eventTypeEnum = UserAccountEventTypeEnum.LOGIN_ATTEMPT_FAILURE;

        UserAccountEventRequest userAccountEventRequest = UserAccountEventRequest
                .builder()
                .type(eventTypeEnum.getCode())
                .description(eventTypeEnum.getDescription())
                .deviceDetails(loginRequest.getDeviceDetails())
                .ipAddress(loginRequest.getIpAddress())
                .build();

        userAccountEventService.addEvent(accountResponse.getUsername(), userAccountEventRequest);

        if (passwordsMatch && authEx instanceof LockedException) {
            return handleApiAuthenticationException(new AccountLockedException());
        }

        if (passwordsMatch && authEx instanceof DisabledException && isAccountEnabled) {
            return handleApiAuthenticationException(new VerificationRequiredException());
        }

        if (passwordsMatch && authEx instanceof DisabledException) {
            return handleApiAuthenticationException(new AccountDisabledException());
        }

        return handleApiAuthenticationException(new ApiAuthenticationException(authEx));
    }

    @ExceptionHandler(ApiAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleApiAuthenticationException(
            final ApiAuthenticationException apiAuthEx) {
        return ExceptionUtil.getApiErrorResponse(apiAuthEx);
    }
}
