package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.PaymentMethod;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.PaymentMethodMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.PaymentMethodResponse;
import com.coinverse.api.common.repositories.PaymentMethodRepository;
import com.coinverse.api.common.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentMethodMapper paymentMethodMapper;

    public List<PaymentMethodResponse> findAll() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        return paymentMethodMapper.paymentMethodsToPaymentMethodsResponse(paymentMethods);
    }

    public PageResponse<PaymentMethodResponse> findAll(Pageable pageable) {
        Page<PaymentMethod> paymentMethodPage = paymentMethodRepository.findAll(pageable);
        return paymentMethodMapper.paymentMethodPageToPaymentMethodResponsePage(paymentMethodPage);
    }

    public PaymentMethodResponse findByCode(String code) {
        PaymentMethod paymentMethod = paymentMethodRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new InvalidRequestException("Invalid code '" + code + "'"));
        return paymentMethodMapper.paymentMethodToPaymentMethodResponse(paymentMethod);
    }
}
