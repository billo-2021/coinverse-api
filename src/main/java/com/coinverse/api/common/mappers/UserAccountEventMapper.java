package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.UserAccountEvent;
import com.coinverse.api.common.models.UserAccountEventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAccountEventMapper {

    @Mapping(source = "event.type.name", target = "event.type")
    UserAccountEventResponse userAccountEventToUserAccountEventResponse(UserAccountEvent userAccountEvent);
}
