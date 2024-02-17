package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.entities.UserPreference;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.UserPreferenceUpdateRequest;
import com.coinverse.api.common.repositories.CurrencyRepository;
import com.coinverse.api.common.repositories.NotificationChannelRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserPreferenceUpdateRequestValidator implements RequestValidator<UserPreferenceUpdateRequest, UserPreference> {
    private final CurrencyRepository currencyRepository;
    private final NotificationChannelRepository notificationChannelRepository;

    @Nullable
    @Override
    public UserPreference validate(UserPreferenceUpdateRequest userPreferenceUpdateRequest) throws InvalidRequestException, MappingException {
        if (userPreferenceUpdateRequest.getCurrencyCode() == null &&
                userPreferenceUpdateRequest.getNotificationMethods() == null) {
            return null;
        }

        final String preferredCurrencyCode = userPreferenceUpdateRequest.getCurrencyCode();
        final Set<String> preferredNotificationMethods = userPreferenceUpdateRequest.getNotificationMethods();

        final UserPreference userPreference = new UserPreference();

        if (preferredCurrencyCode != null) {
            Currency currency = currencyRepository.findByCodeIgnoreCase(preferredCurrencyCode)
                    .orElseThrow(() -> ErrorMessageUtils.getValidationException("preference.currencyCode",
                            preferredCurrencyCode));

            userPreference.setCurrency(currency);
        }

        if (preferredNotificationMethods != null && !preferredNotificationMethods.isEmpty()) {
            Set<NotificationChannel> notificationChannels = preferredNotificationMethods
                    .stream()
                    .map(preferredNotificationMethod -> notificationChannelRepository.findByCodeIgnoreCase(preferredNotificationMethod)
                            .orElseThrow(() -> ErrorMessageUtils.getValidationException("preference.notificationMethods", preferredNotificationMethod))
                    ).collect(Collectors.toSet());

            userPreference.setNotificationChannels(notificationChannels);
        }

        return userPreference;
    }
}
