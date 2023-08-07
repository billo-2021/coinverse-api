package com.coinverse.api.common.models.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "code", "name", "symbol", "circulatingSupply"})
public class CryptoCurrencyResponse {
    private Long id;
    private String code;
    private String name;
    private String symbol;
    private Long circulatingSupply;
}
