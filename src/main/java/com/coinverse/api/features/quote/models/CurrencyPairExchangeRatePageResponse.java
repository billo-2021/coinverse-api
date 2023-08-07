package com.coinverse.api.features.quote.models;

import com.coinverse.api.common.models.PageResponse;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;

import java.util.List;

@JsonPropertyOrder({"count", "total", "currencyPairName", "currencyPairType", "data"})
public class CurrencyPairExchangeRatePageResponse extends PageResponse<CurrencyExchangeRateResponse> {
    private final String currencyPairName;
    private final String currencyPairType;
    public CurrencyPairExchangeRatePageResponse(Integer count,
                                                Long total,
                                                List<CurrencyExchangeRateResponse> data,
                                                String currencyPairName,
                                                String currencyPairType) {
        super(count, total, data);
        this.currencyPairName = currencyPairName;
        this.currencyPairType = currencyPairType;
    }

    public String getCurrencyPairName() {
        return currencyPairName;
    }

    public String getCurrencyPairType() {
        return currencyPairType;
    }

    public static CurrencyPairExchangeRatePageResponse of(
            final Page<CurrencyExchangeRateResponse> page,
            final String currencyPairName,
            final String currencyPairType) {

        return new CurrencyPairExchangeRatePageResponse(
                page.getNumberOfElements(),
                page.getTotalElements(),
                page.toList(),
                currencyPairName,
                currencyPairType
        );
    }
}
