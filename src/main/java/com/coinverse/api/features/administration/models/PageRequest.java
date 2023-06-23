package com.coinverse.api.features.administration.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
        "pageNumber", "pageSize"
})
public class PageRequest {
    private static final Integer MAX_PAGE_SIZE = 100;

    @NotBlank(message = "pageNumber is required")
    @Min(value = 1, message = "pageNumber must be equal or greater than 1")
    private Long pageNumber;

    @NotBlank(message = "pageSize is required")
    @Max(value = 100, message = "pageSize must be equal or less than 100")
    private Integer pageSize;
}
