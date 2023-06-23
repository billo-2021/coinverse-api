package com.coinverse.api.features.authentication.controllers;

import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.features.authentication.models.*;
import com.coinverse.api.features.authentication.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody final RegisterRequest registerRequest
    ) {
        final UserResponse registrationResponse = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody final LoginRequest loginRequest
    ) {
        final LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/request-token")
    public ResponseEntity<String> requestToken(@Valid @RequestBody final TokenRequest tokenRequest) {
        authenticationService.requestToken(tokenRequest);
        return ResponseEntity.ok("Account verification token sent");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyAccount(
            @Valid @RequestBody final TokenVerifyRequest verifyAccountRequest
    ) {
        authenticationService.verifyAccount(verifyAccountRequest);
        return ResponseEntity.ok("Account verified");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> requestResetPasswordToken(@Valid @RequestBody final TokenRequest tokenRequest) {
        authenticationService.requestResetPasswordToken(tokenRequest);
        return ResponseEntity.ok("Password token sent");
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody final ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok("Account password reset");
    }
}
