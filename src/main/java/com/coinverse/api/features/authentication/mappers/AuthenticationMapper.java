package com.coinverse.api.features.authentication.mappers;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.entities.Role;
import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.models.*;
import com.coinverse.api.features.authentication.models.*;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AuthenticationMapper {
    public abstract UserRequest registerRequestToUserRequest(RegisterRequest registerRequest, @Context PasswordEncoder passwordEncoder);

    @Mapping(source = "roleName", target = "roles")
    public abstract AccountRequest registerAccountRequestToAccountRequest(RegisterAccountRequest registerAccountRequest);

    public abstract AddressRequest registerAddressRequestToAddressRequest(RegisterAddressRequest registerAddressRequest);

    public abstract UserPreferenceRequest registerPreferenceRequestToUserPreferenceRequest(RegisterPreferenceRequest registerPreferenceRequest);

//    @Mapping(source = "emailAddress", target = "username")
//    @Mapping(source = "roles", target = "authorities")
//    public abstract UserAccount userToUserPrincipal(User user);

//    @Mapping(source = "username", target = "emailAddress")
//    @Mapping(source = "authorities", target = "roles")
//    public abstract UserResponse userPrincipalToUserResponse(UserAccount userAccount);

    public abstract UserAccount accountToUserAccount(Account account);

    public AccountStatusEnum accountStatusToAccountStatusEnum(AccountStatus accountStatus) {
        return AccountStatusEnum.of(accountStatus.getName()).orElseThrow();
    }

    protected Set<RolePrincipal> rolesToAuthorities(Set<Role> roles) {
        if (roles == null) {
            return Set.of();
        }

        return roles.stream().map(role ->
                        RolePrincipal
                                .builder()
                                .authority(role.getAuthority())
                                .build()
                )
                .collect(Collectors.toSet());
    }


    protected Set<String> authoritiesToRoleNames(Set<RolePrincipal> authorities) {
        if (authorities == null || authorities.isEmpty()) {
            return Collections.<String>emptySet();
        }

        return authorities.stream()
                .map(RolePrincipal::getAuthority)
                .collect(Collectors.toSet());
    }

    protected Set<String> roleNameToRoleNames(String roleName) {
        return Set.of(roleName);
    }

    @AfterMapping
    protected void hashPassword(
            RegisterRequest registerRequest,
            @MappingTarget UserRequest userRequest,
            @Context PasswordEncoder passwordEncoder
    ) {
        String password = passwordEncoder.encode(registerRequest.getAccount().getPassword());

        userRequest.getAccount().setPassword(password);
    }
}
