package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.PaymentMethod;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.PaymentMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMethodMapper {
    PaymentMethodResponse paymentMethodToPaymentMethodResponse(PaymentMethod paymentMethod);
    List<PaymentMethodResponse> paymentMethodsToPaymentMethodsResponse(List<PaymentMethod> paymentMethods);

    default PageResponse<PaymentMethodResponse> paymentMethodPageToPaymentMethodResponsePage(Page<PaymentMethod> paymentMethodPage)  {
        Page<PaymentMethodResponse> paymentMethodResponsePage = paymentMethodPage.map(this::paymentMethodToPaymentMethodResponse);

        return PageResponse.of(paymentMethodResponsePage);
    }
}
