package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.Event;
import com.coinverse.api.common.entities.EventType;
import com.coinverse.api.common.entities.UserAccountEvent;
import com.coinverse.api.common.models.UserAccountEventRequest;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.EventTypeRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountEventValidator {
    private final AccountRepository accountRepository;
    private final EventTypeRepository eventTypeRepository;

    public Account validateUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("account", "username", username));
    }
    public UserAccountEvent validateUserAccountEventRequest(
            @NotNull final String username,
            @NotNull final UserAccountEventRequest userAccountEventRequest) {
        final Account account = validateUsername(username);
        final EventType eventType = eventTypeRepository.findByCodeIgnoreCase(userAccountEventRequest.getType())
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("eventType", "eventTypeName", userAccountEventRequest.getType()));

        final Event event = Event
                .builder()
                .type(eventType)
                .description(userAccountEventRequest.getDescription())
                .build();

        return UserAccountEvent
                .builder()
                .account(account)
                .event(event)
                .deviceDetails(userAccountEventRequest.getDeviceDetails())
                .ipAddress(userAccountEventRequest.getIpAddress())
                .build();
    }
}
