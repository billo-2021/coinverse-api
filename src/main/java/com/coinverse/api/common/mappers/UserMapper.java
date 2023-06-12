package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.core.exceptions.InvalidRequestException;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;

import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@RequiredArgsConstructor
public abstract class UserMapper {
    private final AccountMapper accountMapper;

    public abstract User userRequestToUser(
            UserRequest userRequest,
            @Context AccountStatusRepository accountStatusRepository,
            @Context RoleRepository roleRepository,
            @Context CountryRepository countryRepository,
            @Context CurrencyRepository currencyRepository,
            @Context NotificationChannelRepository notificationChannelRepository
    );

    public abstract UserResponse userToUserResponse(User user);
    public abstract List<UserResponse> usersToUserResponses(List<User> users);

    protected Set<Role> roleNamesToRole(Set<String> roleNames, @Context RoleRepository roleRepository) {
        if (roleNames == null) {
            return null;
        }

        return roleNames.stream()
                .map(roleName ->
                        roleRepository.findByAuthority(roleName)
                                .orElseThrow(() ->
                                        new InvalidRequestException("User role with " + roleName + " does not exist"))
                ).collect(Collectors.toSet());
    }

    protected Account accountRequestToAccount(
            AccountRequest accountRequest,
            @Context AccountStatusRepository accountStatusRepository,
            @Context RoleRepository roleRepository
    ) {
        final String createdStatusName = AccountStatusEnum.CREATED.getName();

        final AccountStatus createdStatus = accountStatusRepository
                .findByName(createdStatusName)
                .orElseThrow();

        final Set<Role> roles = roleNamesToRole(accountRequest.getRoles(), roleRepository);

        return Account.builder()
                .username(accountRequest.getUsername())
                .password(accountRequest.getPassword())
                .roles(roles)
                .status(createdStatus)
                .loginAttempts(0)
                .build();
    }

    protected Address addressRequestToAddress(
            AddressRequest addressRequest,
            @Context CountryRepository countryRepository
    ) {
        Country addressCountry = countryRepository
                .findByCode(addressRequest.getCountryCode())
                .orElseThrow();

        return Address.builder()
                .addressLine(addressRequest.getAddressLine())
                .street(addressRequest.getStreet())
                .country(addressCountry)
                .province(addressRequest.getProvince())
                .city(addressRequest.getCity())
                .postalCode(addressRequest.getPostalCode())
                .build();
    }

    protected UserPreference userPreferenceRequestToUserPreference(
            UserPreferenceRequest userPreferenceRequest,
            @Context CountryRepository countryRepository,
            @Context CurrencyRepository currencyRepository,
            @Context NotificationChannelRepository notificationChannelRepository
    ) {
        Country preferredCountry = countryRepository
                .findByCode(userPreferenceRequest.getCountryCode())
                .orElseThrow();

        Currency preferredCurrency = currencyRepository
                .findByCode(userPreferenceRequest.getCurrencyCode())
                .orElseThrow();

        Set<NotificationChannel> preferredNotificationChannels = userPreferenceRequest
                .getNotificationMethods()
                .stream()
                .map((notificationMethod)
                        -> notificationChannelRepository.findByName(notificationMethod).orElseThrow()
                ).collect(Collectors.toSet());

        return UserPreference.builder()
                .country(preferredCountry)
                .currency(preferredCurrency)
                .notificationChannels(preferredNotificationChannels)
                .build();
    }
}
