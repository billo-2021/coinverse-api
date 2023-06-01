package com.coinverse.api.features.authentication.mappers;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.features.authentication.models.RegisterRequest;
import com.coinverse.api.features.authentication.models.UserPrincipal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthenticationMapper {
    UserRequest registerRequestToUserRequest(RegisterRequest registerRequest);
    UserPrincipal userToUserPrincipal(User user);
    UserResponse userPrincipalToUserResponse(UserPrincipal userPrincipal);
}
