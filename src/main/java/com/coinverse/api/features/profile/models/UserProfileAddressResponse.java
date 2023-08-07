package com.coinverse.api.features.profile.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileAddressResponse {
    private String addressLine;
    private String street;

    private UserProfileCountryResponse country;

    private String province;

    String city;

    private String postalCode;
}
