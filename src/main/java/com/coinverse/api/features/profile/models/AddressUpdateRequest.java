package com.coinverse.api.features.profile.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressUpdateRequest {
    @NotBlank(message = "addressLine is required")
    private String addressLine;

    @NotBlank(message = "street is required")
    private String street;

    @NotBlank(message = "countryCode is required")
    private String countryCode;

    @NotBlank(message = "province is required")
    private String province;

    @NotBlank(message = "city is required")
    String city;

    @NotBlank(message = "postalCode is required")
    private String postalCode;

    private String deviceDetails;
    private String ipAddress;
}
