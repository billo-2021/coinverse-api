package com.coinverse.api.common.security.utils;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.coinverse.api.common.exceptions.ApiException;
import com.coinverse.api.common.security.exceptions.*;
import com.coinverse.api.common.security.models.UserAccount;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationUtils {

    public static void doAuthenticationChecks(UserAccount userAccount) {
        if (!userAccount.getIsEnabled()) {
            throw new AccountDisabledException();
        }

        if (!userAccount.isAccountNonLocked()) {
            throw new AccountLockedException();
        }

        if (!userAccount.isVerified()) {
            throw new VerificationRequiredException();
        }

        if (!userAccount.isAccountNonExpired()) {
            throw new ApiAccountExpiredException();
        }

        if (!userAccount.isCredentialsNonExpired()) {
            throw new AccountCredentialsExpiredException();
        }
    }

    public static ApiAuthenticationException translateAuthenticationException(AuthenticationException authEx,
                                                        boolean passwordsMatch,
                                                        boolean isAccountEnabled) {
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

    public static ApiAuthenticationException translateApiAuthenticationException(ApiAuthenticationException apiAuthEx) {
        if (apiAuthEx instanceof MissingCredentialsException) {
            return new ApiAuthenticationException(HttpStatus.UNAUTHORIZED);
        }

        if (apiAuthEx instanceof AccountLockedException ||
                apiAuthEx instanceof AccountDisabledException ||
                apiAuthEx instanceof VerificationRequiredException) {
            return apiAuthEx;
        }

        return new ApiAuthenticationException();
    }

    public static ApiException translateException(Exception exception) {
        if (exception instanceof JWTVerificationException) {
            return new ApiAuthenticationException();
        }

        return new ApiException();
    }
}
