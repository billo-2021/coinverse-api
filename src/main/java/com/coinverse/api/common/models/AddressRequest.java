package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddressRequest {
    @NotBlank(message = "addressLine is required")
    private String addressLine;

    @NotBlank(message = "street is required")
    private String street;

    @NotBlank(message = "countryCode is required")
    private String countryCode;

    @NotBlank(message = "province is required")
    private String province;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "postalCode is required")
    private String postalCode;
}
