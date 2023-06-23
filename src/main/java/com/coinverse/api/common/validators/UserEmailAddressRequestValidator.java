package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.repositories.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEmailAddressRequestValidator implements RequestValidator<String, User> {
    private final UserRepository userRepository;

    @Override
    public User validate(@NotNull final String emailAddress) throws InvalidRequestException, MappingException {
        return userRepository.findByEmailAddress(emailAddress)
                .orElseThrow(() -> new ValidationException("Invalid email address '" + emailAddress, "emailAddress"));
    }
}
