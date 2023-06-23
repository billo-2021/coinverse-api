package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.*;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {AccountMapper.class}
)
@RequiredArgsConstructor
public abstract class UserMapper {
    public abstract UserResponse userToUserResponse(User user);

    public abstract List<UserResponse> usersToUserResponses(List<User> users);
}
