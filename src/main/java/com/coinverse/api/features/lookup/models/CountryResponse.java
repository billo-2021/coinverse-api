package com.coinverse.api.features.lookup.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CountryResponse {
    private String code;
    private String name;
}
