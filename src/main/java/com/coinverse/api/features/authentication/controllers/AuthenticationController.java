package com.coinverse.api.features.authentication.controllers;

import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.services.DeviceResolutionService;
import com.coinverse.api.common.utils.RequestUtil;
import com.coinverse.api.features.authentication.models.*;
import com.coinverse.api.features.authentication.services.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    private final DeviceResolutionService deviceResolutionService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody final RegisterRequest registerRequest
    ) {
        final UserResponse registrationResponse = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody final LoginRequest loginRequest,
            final HttpServletRequest request
    ) {
        final String deviceDetails = loginRequest.getDeviceDetails();;
        final String ipAddress = loginRequest.getIpAddress();

        if (Objects.isNull(deviceDetails) || deviceDetails.trim().isEmpty()) {
            final String userAgentHeader = request.getHeader("user-agent");

            loginRequest.setDeviceDetails(deviceResolutionService.getDeviceDetails(userAgentHeader));
        }

        if (Objects.isNull(ipAddress) || ipAddress.trim().isEmpty()) {
            loginRequest.setIpAddress(RequestUtil.extractClientIp(request));
        }

        final LoginResponse loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/request-token")
    public ResponseEntity<ApiMessageResponse> requestToken(@Valid @RequestBody final TokenRequest tokenRequest) {
        authenticationService.requestToken(tokenRequest);
        return ResponseEntity.ok(new ApiMessageResponse("Account verification token sent"));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiMessageResponse> verifyAccount(
            @Valid @RequestBody final TokenVerifyRequest verifyAccountRequest
    ) {
        authenticationService.verifyAccount(verifyAccountRequest);
        return ResponseEntity.ok(new ApiMessageResponse("Account verified"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordTokenResponse> requestResetPasswordToken(@Valid @RequestBody final TokenRequest tokenRequest) {
        final ResetPasswordTokenResponse resetPasswordTokenResponse = authenticationService.requestResetPasswordToken(tokenRequest);
        return ResponseEntity.ok(resetPasswordTokenResponse);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ApiMessageResponse> resetPassword(@Valid @RequestBody final ResetPasswordRequest resetPasswordRequest) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(new ApiMessageResponse("Account password reset"));
    }

    @GetMapping("/reset-password/{passwordToken}")
    public ResponseEntity<PasswordTokenUserResponse> requestPasswordTokenUser(@PathVariable String passwordToken) {
        final PasswordTokenUserResponse passwordTokenUsernameResponse =
                authenticationService.requestPasswordTokenUser(passwordToken);
        return ResponseEntity.ok(passwordTokenUsernameResponse);
    }
}
