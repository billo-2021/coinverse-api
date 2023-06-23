package com.coinverse.api.features.profile.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.UserPreferenceUpdateRequest;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.models.UserUpdateRequest;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.profile.models.ProfilePreferenceUpdateRequest;
import com.coinverse.api.features.profile.models.ProfileUpdateRequest;
import com.coinverse.api.features.profile.models.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileRequestValidator {
    private final UserService userService;
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public UserResponse validateUserAccountId(Long accountId) {
        return userService.getUserByAccountId(accountId)
                .orElseThrow(InvalidRequestException::new);
    }
    public UserUpdateRequest validateProfileUpdate(ProfileUpdateRequest userProfileUpdateRequest) {
        ProfilePreferenceUpdateRequest profilePreferenceUpdateRequest = userProfileUpdateRequest.getPreference();

        UserPreferenceUpdateRequest userPreferenceUpdateRequest = null;

        if (profilePreferenceUpdateRequest != null) {
            userPreferenceUpdateRequest = UserPreferenceUpdateRequest
                    .builder()
                    .currencyCode(profilePreferenceUpdateRequest.getCurrencyCode())
                    .notificationMethods(profilePreferenceUpdateRequest.getNotificationMethods())
                    .build();
        }

        return UserUpdateRequest
                .builder()
                .phoneNumber(userProfileUpdateRequest.getPhoneNumber())
                .preference(userPreferenceUpdateRequest)
                .build();
    }

    public Account validateChangePasswordRequest(String username, UpdatePasswordRequest updatePasswordRequest) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(InvalidRequestException::new);

        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), account.getPassword())) {
            throw new InvalidRequestException("Invalid old password provided");
        }

        return account;
    }
}
