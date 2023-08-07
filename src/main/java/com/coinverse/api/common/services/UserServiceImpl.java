package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.UserMapper;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.validators.UserEmailAddressRequestValidator;
import com.coinverse.api.common.validators.UserPreferenceUpdateRequestValidator;
import com.coinverse.api.common.validators.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final WalletRepository walletRepository;
    private final CurrencyRepository currencyRepository;
    private final WalletKeyGenerator walletKeyGenerator;
    private final UserMapper userMapper;
    private final UserRequestValidator userRequestValidator;
    private final UserEmailAddressRequestValidator userEmailAddressRequestValidator;
    private final UserPreferenceUpdateRequestValidator userPreferenceUpdateRequestValidator;

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        final Optional<User> userResponse = userRepository.findById(id);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> getUserByEmailAddress(String emailAddress) {
        final Optional<User> userResponse = userRepository.findByEmailAddressIgnoreCase(emailAddress);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> getUserByAccountId(Long accountId) {
        final Optional<User> userResponse = userRepository.findByAccountId(accountId);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public List<UserResponse> getUsers() {
        final List<User> users = userRepository.findAll();
        return userMapper.usersToUserResponses(users);
    }

    @Transactional
    @Override
    public UserResponse addUser(UserRequest userRequest) {
       final User user = userRequestValidator.validate(userRequest);

       final Account account = user.getAccount();
       accountRepository.saveAndFlush(account);

       final Address address = user.getAddress();
       addressRepository.saveAndFlush(address);

       final UserPreference userPreference = user.getPreference();
       userPreferenceRepository.save(userPreference);

       final User savedUser = userRepository.saveAndFlush(user);
       final List<Currency> walletCurrencies = currencyRepository.findAll();

        final List<Wallet> wallets = walletCurrencies
                .stream()
                .map(walletCurrency -> {
                    final WalletKey walletKey = walletKeyGenerator.generate();

                    return Wallet.
                            builder()
                            .account(account)
                            .currency(walletCurrency)
                            .privateKey(walletKey.getPrivateKey())
                            .publicKey(walletKey.getPublicKey())
                            .address(walletKey.getAddress())
                            .balance(BigDecimal.valueOf(0))
                            .build();
                        }
                ).toList();
        walletRepository.saveAll(wallets);

       return userMapper.userToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        final User existingUser = userRepository.findById(id).orElseThrow(
                () -> new InvalidRequestException("Invalid user id '" + id + "'")
        );

        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());

        final User savedUser = userRepository.save(existingUser);
        return userMapper.userToUserResponse(savedUser);
    }

    @Transactional
    @Override
    public void updateUserByEmailAddress(String emailAddress, UserUpdateRequest userUpdateRequest) {
        String phoneNumber = userUpdateRequest.getPhoneNumber();
        UserPreferenceUpdateRequest userPreferenceUpdateRequest = userUpdateRequest.getPreference();

        User user = userEmailAddressRequestValidator.validate(emailAddress);

        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }

        UserPreference userPreference = userPreferenceUpdateRequestValidator.validate(userPreferenceUpdateRequest);
        updateUserPreference(user, userPreference);

        userRepository.save(user);
    }

    private void updateUserPreference(User user, UserPreference preference) {
        UserPreference userPreference = user.getPreference();

        if (preference == null) {
            return;
        }

        Currency preferredCurrency = preference.getCurrency();
        Set<NotificationChannel> preferredNotificationChannels = preference.getNotificationChannels();

        if (preferredCurrency != null) {
            userPreference.setCurrency(preference.getCurrency());
        }

        if (preferredNotificationChannels != null) {
            userPreference.setNotificationChannels(preferredNotificationChannels);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
