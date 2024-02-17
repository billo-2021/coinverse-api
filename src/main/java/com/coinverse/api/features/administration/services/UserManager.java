package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.repositories.UserRepository;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.administration.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;

    private final UserAccountService userAccountService;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public PageResponse<UserResponse> getUsers(Pageable pageable) {
        final Account userAccount = userAccountService.getCurrentUserAccount();
        final Page<User> usersPage = userRepository.findByAccountIdNot(userAccount.getId(), pageable);

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

    public void addUser(UserRequest userRequest) {
        final String plainPassword = userRequest.getAccount().getPassword();
        final String encodedPassword = passwordEncoder.encode(plainPassword);
        userRequest.getAccount().setPassword(encodedPassword);

        userService.addUser(userRequest);
    }
}
