package com.coinverse.api.features.profile.controllers;

import com.coinverse.api.common.config.routes.ProfileRoutes;
import com.coinverse.api.common.constants.ApiMessage;
import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.models.UserAccountEventTypeEnum;
import com.coinverse.api.common.services.UserAccountEventService;
import com.coinverse.api.features.profile.models.*;
import com.coinverse.api.features.profile.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProfileRoutes.PATH)
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private final UserAccountEventService userAccountEventService;

    @PatchMapping
    ApiMessageResponse updateProfile(@Valid @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        profileService.updateUserProfile(profileUpdateRequest);
        return ApiMessageResponse.of(ApiMessage.USER_PROFILE_UPDATED);
    }

    @PatchMapping(ProfileRoutes.PERSONAL_INFORMATION)
    UserProfileResponse updatePersonalInformation(@Valid @RequestBody
                                                                 PersonalInformationUpdateRequest personalInformationUpdateRequest) {
        final UserProfileResponse userProfileResponse = profileService.updatePersonalInformation(personalInformationUpdateRequest);
        userAccountEventService.addEvent(UserAccountEventTypeEnum.PROFILE_UPDATE);
        return userProfileResponse;
    }

    @PatchMapping(ProfileRoutes.ADDRESS)
    UserProfileResponse updateAddress(@Valid @RequestBody
                                                     AddressUpdateRequest addressUpdateRequest) {
        final UserProfileResponse userProfileResponse = profileService.updateUserAddress(addressUpdateRequest);
        userAccountEventService.addEvent(UserAccountEventTypeEnum.USER_ADDRESS_UPDATE);
        return userProfileResponse;
    }

    @PatchMapping(ProfileRoutes.PREFERENCE)
    UserProfileResponse updatePreference(@Valid @RequestBody
                                                         PreferenceUpdateRequest preferenceUpdateRequest) {

        final UserProfileResponse userProfileResponse = profileService.updateUserPreference(preferenceUpdateRequest);
        userAccountEventService.addEvent(UserAccountEventTypeEnum.USER_PREFERENCE_UPDATE);
        return userProfileResponse;
    }

    @GetMapping
    UserProfileResponse getProfile() {
        return profileService.getUserProfile();
    }
}
