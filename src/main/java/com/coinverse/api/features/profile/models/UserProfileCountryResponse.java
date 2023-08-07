package com.coinverse.api.features.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileCountryResponse {
    private String code;
    private String name;
}
