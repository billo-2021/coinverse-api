package com.coinverse.api.features.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.coinverse.api.features.authentication.config.JwtProperties;
import com.coinverse.api.common.security.models.RolePrincipal;
import com.coinverse.api.common.security.models.UserAccount;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;
    public String issueToken(@NotNull final String username,
                             @NotNull final String emailAddress,
                             @NotNull final List<String> roleNames) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", emailAddress)
                .withClaim("roles", roleNames)
                .sign(Algorithm.HMAC256(properties.secretKey()));
    }

    public DecodedJWT decodeToken(@NotNull final String token) {
        return JWT.require(Algorithm.HMAC256(properties.secretKey()))
                .build()
                .verify(token);
    }

    public UserAccount convertJwtToUserPrincipal(@NotNull final DecodedJWT jwt) {
        return UserAccount.builder()
                .username(jwt.getClaim("email").toString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    private Set<RolePrincipal> extractAuthoritiesFromClaim(@NotNull final DecodedJWT jwt) {
        final Claim claim = jwt.getClaim("roles");

        if (claim.isNull() || claim.isMissing()) {
            return Set.of();
        }

        return new HashSet<>(claim.asList(RolePrincipal.class));
    }

    public String extractUsername(@NotNull final DecodedJWT jwt) {
        return jwt.getSubject();
    }
}
