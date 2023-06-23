package com.coinverse.api.common.security.models;

import com.coinverse.api.common.models.AccountStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class UserAccount implements UserDetails {
    public static final Integer MAX_LOGIN_ATTEMPTS = 3;

    private final Long id;
    private final String username;
    private final String password;
    private Set<RolePrincipal> authorities;
    private AccountStatusEnum status;
    private Boolean isEnabled;
    private Integer loginAttempts;
    private OffsetDateTime lastLoginAt;

    @Override
    public Set<RolePrincipal> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != AccountStatusEnum.EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return loginAttempts < MAX_LOGIN_ATTEMPTS ||
                OffsetDateTime.now(ZoneOffset.UTC).isAfter(lastLoginAt.plusMinutes(60));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != AccountStatusEnum.CREDENTIALS_EXPIRED;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled && isVerified();
    }

    public boolean isVerified() {
        return status == AccountStatusEnum.VERIFIED;
    }
}
