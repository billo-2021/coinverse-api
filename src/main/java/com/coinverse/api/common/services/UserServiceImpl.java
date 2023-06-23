package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.UserMapper;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.validators.UserEmailAddressRequestValidator;
import com.coinverse.api.common.validators.UserPreferenceUpdateRequestValidator;
import com.coinverse.api.common.validators.UserRequestValidator;
import jakarta.validation.constraints.NotNull;
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
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoCurrencyKeyGenerator cryptoCurrencyKeyGenerator;
    private final UserMapper userMapper;
    private final UserRequestValidator userRequestValidator;
    private final UserEmailAddressRequestValidator userEmailAddressRequestValidator;
    private final UserPreferenceUpdateRequestValidator userPreferenceUpdateRequestValidator;

    @Override
    public Optional<UserResponse> getUserById(@NotNull final Long id) {
        final Optional<User> userResponse = userRepository.findById(id);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> getUserByEmailAddress(@NotNull final String emailAddress) {
        final Optional<User> userResponse = userRepository.findByEmailAddress(emailAddress);
        return userResponse.map(userMapper::userToUserResponse);
    }

    @Override
    public Optional<UserResponse> getUserByAccountId(@NotNull final Long accountId) {
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
    public UserResponse addUser(@NotNull final UserRequest userRequest) {
       final User user = userRequestValidator.validate(userRequest);

       final Account account = user.getAccount();
       accountRepository.save(account);

       final Address address = user.getAddress();
       addressRepository.save(address);

       final UserPreference userPreference = user.getPreference();
       userPreferenceRepository.save(userPreference);

       final User savedUser = userRepository.save(user);

        final List<CryptoCurrency> walletCurrencies = cryptoCurrencyRepository.findAll();
        final List<Wallet> wallets = walletCurrencies
                .stream()
                .map(walletCurrency -> {
                    final CryptoCurrencyKey currencyKey = cryptoCurrencyKeyGenerator.generate();

                    return Wallet.
                            builder()
                            .account(account)
                            .currency(walletCurrency)
                            .privateKey(currencyKey.getPrivateKey())
                            .publicKey(currencyKey.getPublicKey())
                            .address(currencyKey.getAddress())
                            .balance(BigDecimal.valueOf(0))
                            .build();
                        }
                ).toList();
        walletRepository.saveAll(wallets);

       return userMapper.userToUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(@NotNull final Long id, @NotNull final UserRequest userRequest) {
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
    public void updateUserByEmailAddress(@NotNull String emailAddress, @NotNull UserUpdateRequest userUpdateRequest) {
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
    public void deleteUser(@NotNull final Long id) {
        userRepository.deleteById(id);
    }
}
