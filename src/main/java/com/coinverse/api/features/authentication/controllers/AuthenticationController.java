package com.coinverse.api.features.authentication.controllers;

import com.coinverse.api.common.models.AuthenticationResponse;
import com.coinverse.api.common.models.LoginRequest;
import com.coinverse.api.common.models.RegisterRequest;
import com.coinverse.api.features.authentication.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest registerRequest
    ) {
        final AuthenticationResponse registrationResponse = authenticationService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody LoginRequest loginRequest
    ) {
//        try {
//            final AuthenticationResponse loginResponse = authenticationService.login(loginRequest);
//            return ResponseEntity.ok(loginResponse);
//        } catch (Exception exception) {
//            System.out.println("Something went wrong" + exception);
//        }
//
//        return ResponseEntity.ok(new AuthenticationResponse());
        throw new BadCredentialsException("Bad credentials");
    }
}
