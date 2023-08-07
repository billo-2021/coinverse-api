package com.coinverse.api.features.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileResponse {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UserProfileAddressResponse address;
    private UserProfilePreferenceResponse preference;
}
