package com.coinverse.api.features.authentication.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({"username", "password", "lastName"})
public class LoginRequest {
    @NotBlank(message = "username is required")
    @Email(message = "Invalid email")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
}
