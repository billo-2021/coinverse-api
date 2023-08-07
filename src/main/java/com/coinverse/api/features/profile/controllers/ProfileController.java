package com.coinverse.api.features.profile.controllers;

import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.services.DeviceResolutionService;
import com.coinverse.api.common.utils.RequestUtil;
import com.coinverse.api.features.profile.models.*;
import com.coinverse.api.features.profile.services.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(ProfileController.PATH)
@RequiredArgsConstructor
public class ProfileController {
    public static final String PATH = "/api/v1/profile";

    private final ProfileService profileService;
    private final DeviceResolutionService deviceResolutionService;

    @PatchMapping
    ApiMessageResponse updateProfile(@Valid @RequestBody final ProfileUpdateRequest profileUpdateRequest) {
        profileService.updateUserProfile(profileUpdateRequest);
        return new ApiMessageResponse("User profile updated");
    }

    @PatchMapping("/personal-information")
    UserProfileResponse updatePersonalInformation(@Valid @RequestBody
                                                                 PersonalInformationUpdateRequest personalInformationUpdateRequest,
                                                  HttpServletRequest request) {
        final String deviceDetails = personalInformationUpdateRequest.getDeviceDetails();;
        final String ipAddress = personalInformationUpdateRequest.getIpAddress();

        if (Objects.isNull(deviceDetails) || deviceDetails.trim().isEmpty()) {
            final String userAgentHeader = request.getHeader("user-agent");

            personalInformationUpdateRequest.setDeviceDetails(deviceResolutionService.getDeviceDetails(userAgentHeader));
        }

        if (Objects.isNull(ipAddress) || ipAddress.trim().isEmpty()) {
            personalInformationUpdateRequest.setIpAddress(RequestUtil.extractClientIp(request));
        }

        return profileService.updatePersonalInformation(personalInformationUpdateRequest);
    }

    @PatchMapping("/address")
    UserProfileResponse updateAddress(@Valid @RequestBody
                                                     AddressUpdateRequest addressUpdateRequest,
                                      HttpServletRequest request) {
        final String deviceDetails = addressUpdateRequest.getDeviceDetails();;
        final String ipAddress = addressUpdateRequest.getIpAddress();

        if (Objects.isNull(deviceDetails) || deviceDetails.trim().isEmpty()) {
            final String userAgentHeader = request.getHeader("user-agent");

            addressUpdateRequest.setDeviceDetails(deviceResolutionService.getDeviceDetails(userAgentHeader));
        }

        if (Objects.isNull(ipAddress) || ipAddress.trim().isEmpty()) {
            addressUpdateRequest.setIpAddress(RequestUtil.extractClientIp(request));
        }

        return profileService.updateUserAddress(addressUpdateRequest);
    }

    @PatchMapping("/preference")
    UserProfileResponse updatePreference(@Valid @RequestBody
                                                         PreferenceUpdateRequest preferenceUpdateRequest,
                                         HttpServletRequest request) {
        final String deviceDetails = preferenceUpdateRequest.getDeviceDetails();;
        final String ipAddress = preferenceUpdateRequest.getIpAddress();

        if (Objects.isNull(deviceDetails) || deviceDetails.trim().isEmpty()) {
            final String userAgentHeader = request.getHeader("user-agent");

            preferenceUpdateRequest.setDeviceDetails(deviceResolutionService.getDeviceDetails(userAgentHeader));
        }

        if (Objects.isNull(ipAddress) || ipAddress.trim().isEmpty()) {
            preferenceUpdateRequest.setIpAddress(RequestUtil.extractClientIp(request));
        }

        return profileService.updateUserPreference(preferenceUpdateRequest);
    }

    @GetMapping
    UserProfileResponse getProfile() {
        return profileService.getUserProfile();
    }
}
