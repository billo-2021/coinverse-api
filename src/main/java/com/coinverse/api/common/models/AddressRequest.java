package com.coinverse.api.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressRequest {
    private String addressLine;
    private String street;
    private String countryCode;
    private String province;
    private String city;
    private String postalCode;
}
