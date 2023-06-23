package com.coinverse.api.features.profile.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
        "oldPassword", "newPassword"
})
public class UpdatePasswordRequest {
    @NotBlank(message = "oldPassword is required")
    private String oldPassword;
    @NotBlank(message = "newPassword is required")
    private String newPassword;
}
