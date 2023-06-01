package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.features.authentication.models.RegisterRequest;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequest userRequest);
    UserResponse userToUserResponse(User user);
    List<UserResponse> usersToUserResponses(List<User> users);
}
