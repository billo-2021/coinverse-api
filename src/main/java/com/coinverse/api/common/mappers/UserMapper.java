package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.models.RegisterRequest;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userRequestToUser(UserRequest userRequest);
    UserRequest registerRequestToUserRequest(RegisterRequest registerRequest);

    UserResponse userToUserResponse(User user);
    List<UserResponse> usersToUserResponses(List<User> users);
}
