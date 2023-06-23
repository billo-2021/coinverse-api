package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.CurrencyRepository;
import com.coinverse.api.features.lookup.mappers.CurrencyMapper;
import com.coinverse.api.features.lookup.models.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public PageResponse<CurrencyResponse> getAllCurrencies() {
        Page<Currency> currencyPage = currencyRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE));
        Page<CurrencyResponse> currencyResponsePage = currencyPage.map(currencyMapper::currencyToCurrencyResponse);

        return PageResponse.of(currencyResponsePage);
    }
}
