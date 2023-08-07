package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.features.authentication.models.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    UserResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    void requestToken(TokenRequest tokenRequest);
    void verifyAccount(TokenVerifyRequest verifyAccountRequest);
    ResetPasswordTokenResponse requestResetPasswordToken(TokenRequest tokenRequest);
    PasswordTokenUserResponse requestPasswordTokenUser(String token);
    void resetPassword(ResetPasswordRequest resetPasswordRequest);
}
