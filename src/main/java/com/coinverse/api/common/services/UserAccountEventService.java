package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.Event;
import com.coinverse.api.common.entities.UserAccountEvent;
import com.coinverse.api.common.mappers.UserAccountEventMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserAccountEventRequest;
import com.coinverse.api.common.models.UserAccountEventResponse;
import com.coinverse.api.common.repositories.EventRepository;
import com.coinverse.api.common.repositories.UserAccountEventRepository;
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

    private final UserAccountEventValidator userAccountEventValidator;
    private final UserAccountEventMapper userAccountEventMapper;

    public void addEvent(String username,
                         UserAccountEventRequest userAccountEventRequest) {
        final UserAccountEvent userAccountEvent = userAccountEventValidator.validateUserAccountEventRequest(username, userAccountEventRequest);
        final Event event = userAccountEvent.getEvent();

        eventRepository.save(event);
        userAccountEventRepository.save(userAccountEvent);
    }

    public PageResponse<UserAccountEventResponse> getUserAccountEvents(String username, Pageable pageable) {
        final Account account = userAccountEventValidator.validateUsername(username);
        Page<UserAccountEvent> userAccountEventPage = userAccountEventRepository.findByAccountId(account.getId(), pageable);

        final Page<UserAccountEventResponse> userAccountEventResponsePage = userAccountEventPage
                .map(userAccountEventMapper::userAccountEventToUserAccountEventResponse);

        return PageResponse.of(userAccountEventResponsePage);
    }
}
