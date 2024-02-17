package com.coinverse.api.features.profile.validators;

import com.coinverse.api.common.models.UserPreferenceUpdateRequest;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.models.UserUpdateRequest;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.profile.models.ProfilePreferenceUpdateRequest;
import com.coinverse.api.features.profile.models.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileRequestValidator {
    private final UserService userService;

    public UserResponse validateUserAccountId(Long accountId) {
        return userService.getUserByAccountId(accountId)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);
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
}
