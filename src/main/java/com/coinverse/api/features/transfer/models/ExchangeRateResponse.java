package com.coinverse.api.features.transfer.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "name", "type", "bidRate", "askRate"})
public class ExchangeRateResponse {
    private Long id;
    private String name;
    private String type;
    private double bidRate;
    private double askRate;
}
