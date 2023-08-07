package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.Event;
import com.coinverse.api.common.entities.EventType;
import com.coinverse.api.common.entities.UserAccountEvent;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.mappers.UserAccountEventMapper;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.EventRepository;
import com.coinverse.api.common.repositories.EventTypeRepository;
import com.coinverse.api.common.repositories.UserAccountEventRepository;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.common.validators.UserAccountEventValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountEventService {
    private final UserAccountEventRepository userAccountEventRepository;
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final AccountRepository accountRepository;
    private final UserAccountService userAccountService;
    private final DeviceDetailsService deviceDetailsService;

    private final UserAccountEventValidator userAccountEventValidator;
    private final UserAccountEventMapper userAccountEventMapper;

    public void addEvent(String username, UserAccountEventTypeEnum eventTypeEnum) {
        final UserAccountEvent userAccountEvent = getUserAccountEvent(eventTypeEnum, username);
        final Event event = userAccountEvent.getEvent();

        eventRepository.save(event);
        userAccountEventRepository.save(userAccountEvent);
    }

    public void addEvent(UserAccountEventTypeEnum eventTypeEnum) {
        final UserAccount currentUser = userAccountService.getCurrentUser();
        addEvent(currentUser.getUsername(), eventTypeEnum);
    }

    public PageResponse<UserAccountEventResponse> getUserAccountEvents(String username, Pageable pageable) {
        final Account account = userAccountEventValidator.validateUsername(username);
        final Page<UserAccountEvent> userAccountEventPage = userAccountEventRepository.findByAccountId(account.getId(), pageable);

        final Page<UserAccountEventResponse> userAccountEventResponsePage = userAccountEventPage
                .map(userAccountEventMapper::userAccountEventToUserAccountEventResponse);

        return PageResponse.of(userAccountEventResponsePage);
    }

    public PageResponse<UserAccountEventResponse> getUserAccountEvents(Pageable pageable) {
        final UserAccount userAccount = userAccountService.getCurrentUser();
        return getUserAccountEvents(userAccount.getUsername(), pageable);
    }

    private UserAccountEvent getUserAccountEvent(UserAccountEventTypeEnum eventTypeEnum, String username) {
        final Account account = getAccount(username);
        final DeviceInformation deviceInformation = deviceDetailsService.getDeviceInfo();
        final EventType eventType = getEventType(eventTypeEnum.getCode());

        final Event event = Event
                .builder()
                .type(eventType)
                .description(eventTypeEnum.getDescription())
                .build();

        return UserAccountEvent
                .builder()
                .account(account)
                .event(event)
                .ipAddress(deviceInformation.getIpAddress())
                .deviceDetails(deviceInformation.getDetails())
                .build();
    }

    private UserAccountEvent getUserAccountEvent(UserAccountEventTypeEnum eventTypeEnum) {
        final UserAccount currentUser = userAccountService.getCurrentUser();
        return getUserAccountEvent(eventTypeEnum, currentUser.getUsername());
    }

    private Account getAccount(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new MappingException("Unable to find account for username '"
                        + username + "'")
                );
    }

    private EventType getEventType(String eventCode) {
        return eventTypeRepository.findByCodeIgnoreCase(eventCode)
                .orElseThrow(() -> new MappingException("Unable to find eventType for eventTypeCode '"
                        + eventCode + "'"));
    }
}
