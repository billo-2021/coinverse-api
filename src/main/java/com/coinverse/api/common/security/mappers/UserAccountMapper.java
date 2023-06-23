package com.coinverse.api.common.security.mappers;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.entities.Role;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.AccountStatusEnum;
import com.coinverse.api.common.security.models.RolePrincipal;
import com.coinverse.api.common.security.models.UserAccount;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserAccountMapper {
    @Mapping(source = "roles", target = "authorities")
    public abstract UserAccount accountToUserAccount(Account account);

    @Nullable
    protected Set<RolePrincipal> rolesToAuthorities(final Set<Role> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream().map(role ->
                        RolePrincipal
                                .builder()
                                .authority(role.getAuthority())
                                .build()
                )
                .collect(Collectors.toSet());
    }

    @Nullable
    protected Set<String> authoritiesToRoleNames(final Set<RolePrincipal> authorities) {
        if (authorities == null) {
            return null;
        }

        return authorities.stream()
                .map(RolePrincipal::getAuthority)
                .collect(Collectors.toSet());
    }

    public AccountStatusEnum accountStatusToAccountStatusEnum(final @NotNull AccountStatus accountStatus) {
        return AccountStatusEnum
                .of(accountStatus.getName())
                .orElseThrow(() ->
                        new MappingException("Invalid account status '" + accountStatus.getName() + "'")
                );
    }
}
