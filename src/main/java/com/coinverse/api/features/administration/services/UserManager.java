package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.UserRepository;
import com.coinverse.api.features.administration.models.*;
import com.coinverse.api.features.administration.validators.UserManagerValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;
    private final UserManagerValidator userManagerValidator;

    public PageResponse<UserResponse> getUsers(@NotNull PageRequest pageRequest) {
        final Page<User> usersPage = userRepository.findAll(pageRequest);

        final Page<UserResponse> userResponsePage = usersPage.map((user) -> {
            final Account account = user.getAccount();
            final Address address = user.getAddress();
            final Country country = address.getCountry();
            final Set<Role> roles = account.getRoles();

            final Set<String> rolesResponse = roles.stream().map(Role::getAuthority)
                    .collect(Collectors.toSet());

            final AccountResponse accountResponse = AccountResponse
                    .builder()
                    .username(account.getUsername())
                    .roles(rolesResponse)
                    .status(account.getStatus().getName())
                    .isEnabled(account.getIsEnabled())
                    .createdAt(account.getCreatedAt())
                    .updatedAt(account.getUpdatedAt())
                    .build();

            final CountryResponse countryResponse = CountryResponse
                    .builder()
                    .code(country.getCode())
                    .name(country.getName())
                    .build();

            final AddressResponse addressResponse = AddressResponse
                    .builder()
                    .addressLine(address.getAddressLine())
                    .street(address.getStreet())
                    .country(countryResponse)
                    .province(address.getProvince())
                    .city(address.getCity())
                    .postalCode(address.getPostalCode())
                    .build();

            return UserResponse
                    .builder()
                    .emailAddress(user.getEmailAddress())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .account(accountResponse)
                    .address(addressResponse)
                    .createdAt(user.getCreatedAt())
                    .updateAt(user.getUpdatedAt())
                    .build();
        });

        return PageResponse.of(userResponsePage);
    }
}
