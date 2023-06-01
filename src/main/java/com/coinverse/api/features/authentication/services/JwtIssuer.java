package com.coinverse.api.features.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.coinverse.api.features.authentication.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(Long userId, String emailAddress, List<String> roleNames) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", emailAddress)
                .withClaim("a", roleNames)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
