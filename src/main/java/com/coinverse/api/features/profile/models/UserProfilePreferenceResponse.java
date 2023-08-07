package com.coinverse.api.features.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfilePreferenceResponse {
    private UserProfileCurrencyResponse currency;
    private List<UserProfileNotificationMethodResponse> notificationMethods;
}
