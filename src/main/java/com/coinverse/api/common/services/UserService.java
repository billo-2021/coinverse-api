package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<UserResponse> getUserById(Long id);
    Optional<UserResponse> getUserByEmailAddress(String emailAddress);
    public List<UserResponse> getUsers();
    UserResponse addUser(UserRequest userRequest);
    UserResponse updateUser(Long id, User user);
    void deleteUser(Long id);
}
