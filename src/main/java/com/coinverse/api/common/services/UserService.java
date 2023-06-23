package com.coinverse.api.common.services;

import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.models.UserUpdateRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponse> getUserById(@NotNull final Long id);
    Optional<UserResponse> getUserByEmailAddress(@NotNull final String emailAddress);
    Optional<UserResponse> getUserByAccountId(@NotNull final Long accountId);
    List<UserResponse> getUsers();
    UserResponse addUser(@NotNull final UserRequest userRequest);
    UserResponse updateUser(@NotNull final Long id, @NotNull final UserRequest userRequest);
    void updateUserByEmailAddress(@NotNull final String emailAddress, @NotNull UserUpdateRequest userUpdateRequest);
    void deleteUser(@NotNull final Long id);
}
