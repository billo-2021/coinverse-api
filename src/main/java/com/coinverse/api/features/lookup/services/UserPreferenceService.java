package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.NotificationChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPreferenceService {
    private final NotificationChannelRepository notificationChannelRepository;

    public PageResponse<String> getNotificationMethods() {
        final Page<NotificationChannel> notificationChannelPage = notificationChannelRepository
                .findAll(PageRequest.of(0, Integer.MAX_VALUE));
        final Page<String> notificationMethodResponse = notificationChannelPage.map(NotificationChannel::getName);

        return PageResponse.of(notificationMethodResponse);
    }
}
