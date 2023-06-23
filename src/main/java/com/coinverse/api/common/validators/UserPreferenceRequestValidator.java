package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.entities.UserPreference;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.UserPreferenceRequest;
import com.coinverse.api.common.repositories.CurrencyRepository;
import com.coinverse.api.common.repositories.NotificationChannelRepository;
import jakarta.validation.constraints.NotNull;
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
    public UserPreference validate(@NotNull final UserPreferenceRequest userPreferenceRequest) throws InvalidRequestException, MappingException {
        final Currency preferredCurrency = currencyRepository
                .findByCode(userPreferenceRequest.getCurrencyCode())
                .orElseThrow(() ->
                        new ValidationException("Invalid preferred currency '" +
                                userPreferenceRequest.getCurrencyCode() + "'", "preference.currencyCode")
                );

        final Set<NotificationChannel> preferredNotificationChannels = userPreferenceRequest
                .getNotificationMethods()
                .stream()
                .map((notificationMethod) ->
                        notificationChannelRepository.findByName(notificationMethod)
                                .orElseThrow(() ->
                                        new ValidationException("Invalid notification method '" +
                                                notificationMethod + "'", "preference.notificationMethods")
                                )
                ).collect(Collectors.toSet());

        return UserPreference.builder()
                .currency(preferredCurrency)
                .notificationChannels(preferredNotificationChannels)
                .build();
    }
}
