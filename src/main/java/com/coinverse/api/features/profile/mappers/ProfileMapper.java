package com.coinverse.api.features.profile.mappers;

import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.entities.User;
import com.coinverse.api.features.profile.models.UserProfileNotificationMethodResponse;
import com.coinverse.api.features.profile.models.UserProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProfileMapper {

    @Mapping(source = "preference.notificationChannels", target = "preference.notificationMethods")
    UserProfileResponse userToUserProfileResponse(User user);
    UserProfileNotificationMethodResponse notificationChannelToNotificationMethodResponse(NotificationChannel notificationChannel);
}
