package com.coinverse.api.features.authentication.mappers;

import com.coinverse.api.common.models.*;
import com.coinverse.api.features.authentication.models.*;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AuthenticationMapper {
    @BeanMapping(builder = @Builder(disableBuilder = true))
    public abstract UserRequest registerRequestToUserRequest(final RegisterRequest registerRequest, @Context final PasswordEncoder passwordEncoder);

    @Mapping(source = "roleName", target = "roles")
    public abstract AccountRequest registerAccountRequestToAccountRequest(final RegisterAccountRequest registerAccountRequest, @Context final PasswordEncoder passwordEncoder);

    public abstract AddressRequest registerAddressRequestToAddressRequest(final RegisterAddressRequest registerAddressRequest);

    public abstract UserPreferenceRequest registerPreferenceRequestToUserPreferenceRequest(final RegisterPreferenceRequest registerPreferenceRequest);

    protected Set<String> roleNameToRoleNames(final String roleName) {
        return Set.of(roleName);
    }
}
