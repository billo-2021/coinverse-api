package com.coinverse.api.features.authentication.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"username", "password", "roleName"})
public class RegisterAccountRequest {
    @NotBlank(message = "username is required")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "password is required")
    private String password;
}
