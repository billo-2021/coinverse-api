package com.coinverse.api.features.authentication.mappers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.coinverse.api.features.authentication.models.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecodedJwtMapper {
    public UserPrincipal toUserPrincipal(DecodedJWT jwt) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(jwt.getSubject()))
                .username(jwt.getClaim("email").toString())
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }

    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt) {
        var claim = jwt.getClaim("a");

        if (claim.isNull() || claim.isMissing()) {
            return List.of();
        }

        return claim.asList(SimpleGrantedAuthority.class);
    }
}
