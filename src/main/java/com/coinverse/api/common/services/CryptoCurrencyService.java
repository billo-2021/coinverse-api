package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.CryptoCurrency;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.CryptoCurrencyMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CryptoCurrencyResponse;
import com.coinverse.api.common.repositories.CryptoCurrencyRepository;
import com.coinverse.api.common.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    public List<CryptoCurrencyResponse> findAll() {
        final List<CryptoCurrency> cryptoCurrencies = cryptoCurrencyRepository.findAll();

        return cryptoCurrencyMapper.cryptoCurrenciesToCryptoCurrenciesResponse(cryptoCurrencies);
    }

    public PageResponse<CryptoCurrencyResponse> findAll(Pageable pageable) {
        final Page<CryptoCurrency> cryptoCurrencyPage = cryptoCurrencyRepository.findAll(pageable);

        return cryptoCurrencyMapper.cryptoCurrencyPageToCryptoCurrencyPageResponse(cryptoCurrencyPage);
    }

    public CryptoCurrencyResponse findByCurrencyCode(String code) {
        final CryptoCurrency cryptoCurrency = cryptoCurrencyRepository.findByCurrencyCodeIgnoreCase(code)
                .orElseThrow(() -> new InvalidRequestException("Invalid currency code '" + code + "'"));

        return cryptoCurrencyMapper.cryptoCurrencyToCryptoCurrencyResponse(cryptoCurrency);
    }
}
