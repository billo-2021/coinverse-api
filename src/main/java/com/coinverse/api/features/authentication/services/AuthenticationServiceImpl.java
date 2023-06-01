package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.mappers.UserMapper;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.authentication.models.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(
            UserService userService,
            UserMapper userMapper,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        final String passwordHash = passwordEncoder.encode(registerRequest.getPassword());
        final UserRequest userRequest = userMapper.registerRequestToUserRequest(registerRequest);
        userRequest.setPasswordHash(passwordHash);
        userRequest.setPasswordSalt("123");

        final UserResponse userResponse = userService.addUser(userRequest);
        final String jwtToken = jwtService.generateToken(userResponse);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        final String emailAddress = loginRequest.getEmailAddress();
        final String password = loginRequest.getPassword();

        var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(emailAddress, password);
        authenticationManager.authenticate(usernamePasswordAuthToken);

        final Optional<UserResponse> userResponse = userService.getUserByEmailAddress(emailAddress);

        if (userResponse.isEmpty()) {
            throw new RuntimeException("User with email " + emailAddress + " does not exist");
        }

        var user = userResponse.get();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private UserPrincipal getUserDetails(String emailAddress) {

    }
}
