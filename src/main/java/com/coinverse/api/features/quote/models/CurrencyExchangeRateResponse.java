package com.coinverse.api.features.quote.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "bidRate", "askRate", "createdId", "timeToLive", "updatedAt"})
public class CurrencyExchangeRateResponse {
    private Long id;
    private double bidRate;
    private double askRate;
    private OffsetDateTime createdAt;
    private Integer timeToLive;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime updatedAt;
}
