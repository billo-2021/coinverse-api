package com.coinverse.api.features.authentication.mappers;

import com.coinverse.api.common.models.*;
import com.coinverse.api.features.authentication.models.*;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AuthenticationMapper {
    @BeanMapping(builder = @Builder(disableBuilder = true))
    public abstract UserRequest registerRequestToUserRequest(RegisterRequest registerRequest, @Context PasswordEncoder passwordEncoder);

    public abstract AccountRequest registerAccountRequestToAccountRequest(RegisterAccountRequest registerAccountRequest, @Context PasswordEncoder passwordEncoder);

    public abstract AddressRequest registerAddressRequestToAddressRequest(RegisterAddressRequest registerAddressRequest);

    public abstract UserPreferenceRequest registerPreferenceRequestToUserPreferenceRequest(RegisterPreferenceRequest registerPreferenceRequest);

    protected Set<String> roleNameToRoleNames(String roleName) {
        return Set.of(roleName);
    }
}
