package com.coinverse.api.common.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest {
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
    private AccountRequest account;

    @NotNull(message = "address is required")
    @Valid
    private AddressRequest address;

    @NotNull(message = "preference is required")
    @Valid
    private UserPreferenceRequest preference;
}
