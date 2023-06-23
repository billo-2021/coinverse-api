package com.coinverse.api.features.administration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@AllArgsConstructor
@Data
@Builder
public class AddressResponse {
    private String addressLine;
    private String street;
    private CountryResponse country;
    private String province;
    private String city;
    private String postalCode;
}
