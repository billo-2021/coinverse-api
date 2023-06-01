package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.AuthenticationResponse;
import com.coinverse.api.common.models.LoginRequest;
import com.coinverse.api.common.models.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse login(LoginRequest loginRequest);
}
