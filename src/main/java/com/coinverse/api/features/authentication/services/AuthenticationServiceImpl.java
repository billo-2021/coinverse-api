package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.*;
import com.coinverse.api.common.security.services.UserAuthenticationService;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.common.security.services.JwtService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.authentication.mappers.AuthenticationMapper;
import com.coinverse.api.features.authentication.models.*;
import com.coinverse.api.features.messaging.models.MessagingChannelEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserAuthenticationService userAuthenticationService;
    private final UserService userService;
    private final AuthenticationMapper authenticationMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AccountVerificationService accountVerificationService;

    private final ResetPasswordService resetPasswordService;

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        final UserRequest userRequest = authenticationMapper.registerRequestToUserRequest(registerRequest, passwordEncoder);
        final RoleEnum roleEnum = RoleEnum.CUSTOMER;
        userRequest.getAccount().setRoles(Set.of(roleEnum.getAuthority()));

        final UserResponse userResponse = userService.addUser(userRequest);
        final AccountResponse accountResponse = userResponse.getAccount();
        checkToRequestToken(accountResponse);

        return userResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        final AccountResponse accountResponse = userAuthenticationService.authenticate(loginRequest.getUsername(),
                loginRequest.getPassword());

        checkToRequestToken(accountResponse);
        return getLoginResponse(accountResponse);
    }

    @Override
    public void requestToken(TokenRequest tokenRequest) {
        accountVerificationService.requestToken(tokenRequest);
    }

    @Override
    public void verifyAccount(TokenVerifyRequest verifyAccountRequest) {
        accountVerificationService.verifyAccount(verifyAccountRequest);
    }

    @Override
    public ResetPasswordTokenResponse requestResetPasswordToken(TokenRequest tokenRequest) {
        return resetPasswordService.requestResetPasswordToken(tokenRequest);
    }

    @Override
    public PasswordTokenUserResponse requestPasswordTokenUser(String token) {
        return resetPasswordService.getPasswordTokenUser(token);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        resetPasswordService.resetPassword(resetPasswordRequest);
    }

    private void checkToRequestToken(AccountResponse accountResponse) {
        if (accountResponse.getStatus() != AccountStatusEnum.PENDING_VERIFICATION) {
            return;
        }

        final TokenRequest tokenRequest = TokenRequest.builder()
                .username(accountResponse.getUsername())
                .messagingChannel(MessagingChannelEnum.EMAIL.getCode())
                .build();

        requestToken(tokenRequest);
    }

    private LoginResponse getLoginResponse(AccountResponse accountResponse) {
        final UserResponse userResponse = userService.getUserByAccountId(accountResponse.getId())
                .orElseThrow(ErrorMessageUtils::getInvalidCredentialsException);

        final List<String> roles = accountResponse.getRoles().stream().toList();

        final String token = jwtService.issueToken(
                accountResponse.getUsername(),
                accountResponse.getUsername(),
                roles
        );

        return LoginResponse
                .builder()
                .user(userResponse)
                .accessToken(token)
                .build();
    }
}
