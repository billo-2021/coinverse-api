package com.coinverse.api.common.services;

import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.models.UserUpdateRequest;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserResponse> getUserById(Long id);
    Optional<UserResponse> getUserByEmailAddress(String emailAddress);
    Optional<UserResponse> getUserByAccountId(Long accountId);
    List<UserResponse> getUsers();
    UserResponse addUser(UserRequest userRequest);
    UserResponse updateUser(Long id, UserRequest userRequest);
    void updateUserByEmailAddress(String emailAddress, UserUpdateRequest userUpdateRequest);
    void deleteUser(Long id);
}
