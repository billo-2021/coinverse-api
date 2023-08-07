package com.coinverse.api.common.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "type", "code", "name", "symbol"})
public class CurrencyResponse {
    private Long id;
    private String type;
    private String code;
    private String name;
    private String symbol;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CountryResponse> countries;
}
