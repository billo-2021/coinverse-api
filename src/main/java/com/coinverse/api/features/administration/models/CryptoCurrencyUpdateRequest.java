package com.coinverse.api.features.administration.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CryptoCurrencyUpdateRequest {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "symbol is required")
    private String Symbol;

    @NotNull(message = "circulatingSupply is required")
    @Min(value = 1, message = "circulatingSupply must be greater than 1")
    private Long circulatingSupply;
}
