package com.coinverse.api.features.profile.controllers;

import com.coinverse.api.features.profile.models.ProfileUpdateRequest;
import com.coinverse.api.features.profile.models.UpdatePasswordRequest;
import com.coinverse.api.features.profile.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ProfileController.PATH)
@RequiredArgsConstructor
public class ProfileController {
    public static final String PATH = "/api/v1/profile";

    private final ProfileService profileService;

    @PatchMapping
    ResponseEntity<String> updateProfile(@Valid @RequestBody final ProfileUpdateRequest profileUpdateRequest) {
        profileService.updateUserProfile(profileUpdateRequest);
        return ResponseEntity.ok("User profile updated");
    }

    @PatchMapping("/change-password")
    ResponseEntity<String> changePassword(@Valid @RequestBody final UpdatePasswordRequest updatePasswordRequest) {
        profileService.updatePassword(updatePasswordRequest);
        return ResponseEntity.ok("User account password updated");
    }
}
