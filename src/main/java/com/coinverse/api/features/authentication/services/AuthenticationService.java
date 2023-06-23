package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.features.authentication.models.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    UserResponse register(@NotNull final RegisterRequest registerRequest);
    LoginResponse login(@NotNull final LoginRequest loginRequest);
    void requestToken(@NotNull final TokenRequest tokenRequest);
    void verifyAccount(@NotNull final TokenVerifyRequest verifyAccountRequest);
    void requestResetPasswordToken(@NotNull final TokenRequest tokenRequest);
    void resetPassword(@NotNull final ResetPasswordRequest resetPasswordRequest);
}
