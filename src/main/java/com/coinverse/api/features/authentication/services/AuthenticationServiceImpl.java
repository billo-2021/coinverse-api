package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.*;
import com.coinverse.api.common.security.exceptions.*;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.features.authentication.exceptions.LoginAuthenticationException;
import com.coinverse.api.features.authentication.mappers.AuthenticationMapper;
import com.coinverse.api.features.authentication.models.*;
import com.coinverse.api.features.authentication.validators.LoginRequestValidator;
import com.coinverse.api.features.messaging.models.MessagingChannel;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final AccountService accountService;
    private final AuthenticationMapper authenticationMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccountVerificationService accountVerificationService;
    private final LoginRequestValidator loginRequestValidator;

    private final ResetPasswordService resetPasswordService;

    @Override
    public UserResponse register(@NotNull final RegisterRequest registerRequest) {
        final UserRequest userRequest = authenticationMapper.registerRequestToUserRequest(registerRequest, passwordEncoder);
        final UserResponse userResponse = userService.addUser(userRequest);
        final AccountResponse accountResponse = userResponse.getAccount();

        checkToRequestToken(accountResponse);

        return userResponse;
    }

    @Override
    public LoginResponse login(@NotNull final LoginRequest loginRequest) {
        final AccountResponse accountResponse = loginRequestValidator.validate(loginRequest);

        try {
            final String username = loginRequest.getUsername();
            final String password = loginRequest.getPassword();

            final UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            final Authentication authentication = authenticationManager.
                    authenticate(usernamePasswordAuthToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserAccount userPrincipal = (UserAccount) authentication.getPrincipal();

            final UserResponse userResponse = userService.getUserByAccountId(userPrincipal.getId())
                    .orElseThrow(InvalidCredentialsException::new);

            accountService.resetAccountLoginAttemptsById(accountResponse.getId());

            final List<String> roles = userPrincipal
                    .getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            checkToRequestToken(accountResponse);

            final String token = jwtService.issueToken(
                    userPrincipal.getUsername(),
                    userPrincipal.getUsername(),
                    roles
            );

            return LoginResponse
                    .builder()
                    .user(userResponse)
                    .accessToken(token)
                    .build();
        } catch (AuthenticationException ex) {
            throw new LoginAuthenticationException(loginRequest, accountResponse, ex);
        }
    }

    @Override
    public void requestToken(@NotNull final TokenRequest tokenRequest) {
        accountVerificationService.requestToken(tokenRequest);
    }

    @Override
    public void verifyAccount(@NotNull final TokenVerifyRequest verifyAccountRequest) {
        accountVerificationService.verifyAccount(verifyAccountRequest);
    }

    @Override
    public void requestResetPasswordToken(@NotNull final TokenRequest tokenRequest) {
        resetPasswordService.requestResetPasswordToken(tokenRequest);
    }

    @Override
    public void resetPassword(@NotNull final ResetPasswordRequest resetPasswordRequest) {
        resetPasswordService.resetPassword(resetPasswordRequest);
    }

    private void checkToRequestToken(@NotNull final AccountResponse accountResponse) {
        if (accountResponse.getStatus() != AccountStatusEnum.PENDING_VERIFICATION) {
            return;
        }

        final TokenRequest tokenRequest = TokenRequest.builder()
                .username(accountResponse.getUsername())
                .messagingChannel(MessagingChannel.EMAIL.getName())
                .build();

        requestToken(tokenRequest);
    }
}
