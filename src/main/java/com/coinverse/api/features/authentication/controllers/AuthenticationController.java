package com.coinverse.api.features.authentication.controllers;

import com.coinverse.api.common.config.routes.AuthenticationRoutes;
import com.coinverse.api.common.constants.ApiMessage;
import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.features.authentication.models.*;
import com.coinverse.api.features.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AuthenticationRoutes.PATH)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(AuthenticationRoutes.REGISTER)
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        final UserResponse registrationResponse = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @PostMapping(AuthenticationRoutes.LOGIN)
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        final LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(AuthenticationRoutes.REQUEST_TOKEN)
    public ApiMessageResponse requestToken(@Valid @RequestBody TokenRequest tokenRequest) {
        authenticationService.requestToken(tokenRequest);
        return ApiMessageResponse.of(ApiMessage.ACCOUNT_VERIFICATION_TOKEN_SENT);
    }

    @PostMapping(AuthenticationRoutes.VERIFY)
    public ApiMessageResponse verifyAccount(
            @Valid @RequestBody final TokenVerifyRequest verifyAccountRequest
    ) {
        authenticationService.verifyAccount(verifyAccountRequest);
        return ApiMessageResponse.of(ApiMessage.ACCOUNT_VERIFIED);
    }

    @PostMapping(AuthenticationRoutes.RESET_PASSWORD)
    public ResetPasswordTokenResponse requestResetPasswordToken(@Valid @RequestBody TokenRequest tokenRequest) {
        return authenticationService.requestResetPasswordToken(tokenRequest);
    }

    @PatchMapping(AuthenticationRoutes.RESET_PASSWORD)
    public ApiMessageResponse resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ApiMessageResponse.of(ApiMessage.ACCOUNT_PASSWORD_RESET);
    }

    @GetMapping(AuthenticationRoutes.REQUEST_PASSWORD_TOKEN_USER)
    public PasswordTokenUserResponse requestPasswordTokenUser(@PathVariable String passwordToken) {
        return authenticationService.requestPasswordTokenUser(passwordToken);
    }
}
