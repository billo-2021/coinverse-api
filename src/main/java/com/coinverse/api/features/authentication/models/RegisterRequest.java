package com.coinverse.api.features.authentication.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        "emailAddress", "firstName", "lastName",
        "phoneNumber", "account", "address", "preference"
})
public class RegisterRequest {
    @NotBlank(message = "emailAddress is required")
    @Email(message = "Invalid email")
    private String emailAddress;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotNull(message = "account is required")
    @Valid
    private RegisterAccountRequest account;

    @NotNull(message = "address is required")
    @Valid
    private RegisterAddressRequest address;

    @NotNull(message = "preference is required")
    @Valid
    private RegisterPreferenceRequest preference;
}
