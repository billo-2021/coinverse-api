package com.coinverse.api.features.authentication.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder({
        "username", "token", "password"
})
public class ResetPasswordRequest {
    @NotBlank(message = "username is required")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "token is required")
    private String token;

    @NotBlank(message = "password is required")
    private String password;
}
