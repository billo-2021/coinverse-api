package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.CurrencyPair;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.CurrencyPairMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CurrencyPairResponse;
import com.coinverse.api.common.repositories.CurrencyPairRepository;
import com.coinverse.api.common.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyPairService {
    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyPairMapper currencyPairMapper;

    public List<CurrencyPairResponse> findAllCurrencyPairs() {
        final List<CurrencyPair> currencyPairs = currencyPairRepository.findAll();
        return currencyPairMapper.currencyPairsToCurrencyPairsResponse(currencyPairs);
    }

    public PageResponse<CurrencyPairResponse> findAllCurrencyPairs(Pageable pageable) {
        final Page<CurrencyPair> currencyPairPage = currencyPairRepository.findAll(pageable);
        return currencyPairMapper.currencyPairsPageToCurrencyPairsPageResponse(currencyPairPage);
    }

    public CurrencyPairResponse findByName(String name) {
        final CurrencyPair currencyPair = currencyPairRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new InvalidRequestException("Invalid name '" + name + "'"));

        return currencyPairMapper.currencyPairToCurrencyPairResponse(currencyPair);
    }

    public List<CurrencyPairResponse> findAllByType(String currencyType) {
        final List<CurrencyPair> currencyPairs = currencyPairRepository.findByTypeNameIgnoreCase(currencyType);
        return currencyPairMapper.currencyPairsToCurrencyPairsResponse(currencyPairs);
    }

    public PageResponse<CurrencyPairResponse> findAllByType(String currencyType, Pageable pageable) {
        final Page<CurrencyPair> currencyPairPage = currencyPairRepository.findByTypeNameIgnoreCase(currencyType, pageable);

        return currencyPairMapper.currencyPairsPageToCurrencyPairsPageResponse(currencyPairPage);
    }
}
