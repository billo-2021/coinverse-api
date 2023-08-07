package com.coinverse.api.features.profile.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonalInformationUpdateRequest {
    @NotBlank(message = "emailAddress is required")
    @Email(message = "Invalid email")
    private String emailAddress;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;
}
