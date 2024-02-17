package com.coinverse.api.features.administration.models;

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
public class UpdateAccountEnabledRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotNull(message = "isEnabled is required")
    private Boolean isEnabled;
}
