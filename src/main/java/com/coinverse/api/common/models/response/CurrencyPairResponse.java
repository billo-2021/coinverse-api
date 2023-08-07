package com.coinverse.api.common.models.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "type", "name", "baseCurrency", "quoteCurrency"})
public class CurrencyPairResponse {
    private Long id;
    private String type;
    private String name;
    private CurrencyResponse baseCurrency;
    private CurrencyResponse quoteCurrency;
}
