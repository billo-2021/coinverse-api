package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRequestValidator implements RequestValidator<UserRequest, User> {
    private final AccountRequestValidator accountRequestValidator;
    private final AddressRequestValidator addressRequestValidator;
    private final UserPreferenceRequestValidator userPreferenceRequestValidator;
    private final UserRepository userRepository;

    @Override
    public User validate(@NotNull final UserRequest userRequest) throws InvalidRequestException, MappingException {
        userRepository.findByEmailAddress(userRequest.getEmailAddress())
                .ifPresent((user) -> {
                    throw new ValidationException("User with email address '" +
                            user.getEmailAddress() + "' already exists", "emailAddress");
                        }
                );

        AccountRequest accountRequest = userRequest.getAccount();
        AddressRequest addressRequest = userRequest.getAddress();
        UserPreferenceRequest userPreferenceRequest = userRequest.getPreference();

        Account account = accountRequestValidator.validate(accountRequest);
        Address address = addressRequestValidator.validate(addressRequest);
        UserPreference preference = userPreferenceRequestValidator.validate(userPreferenceRequest);

        return User.builder()
                .emailAddress(userRequest.getEmailAddress())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .phoneNumber(userRequest.getPhoneNumber())
                .account(account)
                .address(address)
                .preference(preference)
                .build();
    }
}
