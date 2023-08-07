package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.CurrencyMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CurrencyResponse;
import com.coinverse.api.common.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyResponse> findAll() {
        final List<Currency> currencies = currencyRepository.findAll();
        return currencyMapper.currenciesToCurrenciesResponse(currencies);
    }

    public PageResponse<CurrencyResponse> findAll(Pageable pageable) {
        final Page<Currency> currencyPage = currencyRepository.findAll(pageable);
        return currencyMapper.currencyPageToCurrencyPageResponse(currencyPage);
    }

    public CurrencyResponse findByCode(String code) {
        final Currency currency = currencyRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new InvalidRequestException("Invalid code '" + code + "'"));

        return currencyMapper.currencyToCurrencyResponse(currency);
    }

    public List<CurrencyResponse> findAllByType(String type) {
        final List<Currency> currencies = currencyRepository.findByTypeNameIgnoreCase(type);

        return currencyMapper.currenciesToCurrenciesResponse(currencies);
    }

    public PageResponse<CurrencyResponse> findAllByType(String type, Pageable pageable) {
        final Page<Currency> currencyPage = currencyRepository.findByTypeNameIgnoreCase(type, pageable);

        return currencyMapper.currencyPageToCurrencyPageResponse(currencyPage);
    }
}
