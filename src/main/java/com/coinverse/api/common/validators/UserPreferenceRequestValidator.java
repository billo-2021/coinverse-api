package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.entities.UserPreference;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.UserPreferenceRequest;
import com.coinverse.api.common.repositories.CurrencyRepository;
import com.coinverse.api.common.repositories.NotificationChannelRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPreferenceRequestValidator implements RequestValidator<UserPreferenceRequest, UserPreference> {
    private final CurrencyRepository currencyRepository;
    private final NotificationChannelRepository notificationChannelRepository;

    @Override
    public UserPreference validate(UserPreferenceRequest userPreferenceRequest) throws InvalidRequestException, MappingException {
        final Currency preferredCurrency = currencyRepository
                .findByCodeIgnoreCase(userPreferenceRequest.getCurrencyCode())
                .orElseThrow(() ->
                        ErrorMessageUtils.getValidationException("preference.currencyCode", userPreferenceRequest.getCurrencyCode())
                );

        final Set<NotificationChannel> preferredNotificationChannels = userPreferenceRequest
                .getNotificationMethods()
                .stream()
                .map((notificationMethod) ->
                        notificationChannelRepository.findByCodeIgnoreCase(notificationMethod)
                                .orElseThrow(() ->
                                        ErrorMessageUtils.getValidationException("preference.notificationMethods", notificationMethod)
                                )
                ).collect(Collectors.toSet());

        return UserPreference.builder()
                .currency(preferredCurrency)
                .notificationChannels(preferredNotificationChannels)
                .build();
    }
}
