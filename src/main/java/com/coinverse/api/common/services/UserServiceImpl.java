package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.entities.UserRole;
import com.coinverse.api.common.mappers.UserMapper;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.repositories.UserRepository;
import com.coinverse.api.common.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        Optional<User> userResponse = userRepository.findById(id);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> getUserByEmailAddress(String emailAddress) {
        Optional<User> userResponse = userRepository.findByEmailAddress(emailAddress);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.usersToUserResponses(users);
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        final String roleName = userRequest.getRoleName();
        final Optional<UserRole> userRoleResponse = userRoleRepository.findByName("admin");

        if (userRoleResponse.isEmpty()) {
            throw new RuntimeException("User role with " + "admin" + " does not exist");
        }

        UserRole userRole = userRoleResponse.get();
        User user = userMapper.userRequestToUser(userRequest);
        user.setRole(userRole);
        User savedUser = userRepository.save(user);

        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, User user) {
        var dbUserResult = userRepository.findById(id);

        if (dbUserResult.isEmpty()) {
            throw new RuntimeException("User with id " + id + " does not exist");
        }

        var dbUser = dbUserResult.get();
        dbUser.setFirstName(user.getFirstName());
        dbUser.setLastName(user.getLastName());

        User savedUser = userRepository.save(dbUser);
        return userMapper.userToUserResponse(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
