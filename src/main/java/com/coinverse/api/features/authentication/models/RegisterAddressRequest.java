package com.coinverse.api.features.authentication.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({
        "addressLine", "street", "countryCode",
        "province", "city", "postalCode"
})
public class RegisterAddressRequest {
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
}
