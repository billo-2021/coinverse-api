package com.coinverse.api.common.services;

import com.coinverse.api.common.exceptions.TokenExpiredException;
import com.coinverse.api.common.exceptions.TokenInvalidException;
import com.coinverse.api.common.models.StringToken;
import org.springframework.stereotype.Component;

@Component
public class StringTokenService {
    public StringToken generate(StringTokenGenerator generator) {
        return generator.generate();
    }

    public void validateToken(StringToken token, String otherToken) {
        if (token.hasExpired()) {
            throw new TokenExpiredException("Token has expired");
        }

        if (!token.matches(otherToken)) {
            throw new TokenInvalidException("Invalid token");
        }
    }
}
