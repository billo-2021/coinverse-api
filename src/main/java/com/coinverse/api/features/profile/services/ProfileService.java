package com.coinverse.api.features.profile.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.models.UserUpdateRequest;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.profile.mappers.ProfileMapper;
import com.coinverse.api.features.profile.models.*;
import com.coinverse.api.features.profile.validators.ProfileRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final NotificationChannelRepository notificationChannelRepository;
    private final CurrencyRepository currencyRepository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final ProfileRequestValidator profileRequestValidator;
    private final ProfileMapper profileMapper;

    @Transactional
    public void updateUserProfile(ProfileUpdateRequest profileUpdateRequest) {
        final UserAccount userPrincipal = userAccountService.getCurrentUser();

        UserResponse userResponse = profileRequestValidator.validateUserAccountId(userPrincipal.getId());
        String userEmailAddress = userResponse.getEmailAddress();

        UserUpdateRequest userUpdateRequest = profileRequestValidator
                .validateProfileUpdate(profileUpdateRequest);
        userService.updateUserByEmailAddress(userEmailAddress, userUpdateRequest);
    }

    public UserProfileResponse getUserProfile() {
        final UserAccount userAccount = userAccountService.getCurrentUser();
        final User user = userRepository.findByAccountId(userAccount.getId())
                .orElseThrow(() -> new MappingException("Invalid username '" + userAccount.getUsername() + "'"));

        return profileMapper.userToUserProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updatePersonalInformation(PersonalInformationUpdateRequest personalInformationUpdateRequest) {
        final UserAccount userAccount = userAccountService.getCurrentUser();
        final User user = userRepository.findByAccountId(userAccount.getId())
                .orElseThrow(() -> new MappingException("Invalid username '" + userAccount.getUsername() + "'"));

        user.setEmailAddress(personalInformationUpdateRequest.getEmailAddress());
        user.setFirstName(personalInformationUpdateRequest.getFirstName());
        user.setLastName(personalInformationUpdateRequest.getLastName());
        user.setPhoneNumber(personalInformationUpdateRequest.getPhoneNumber());

        User savedUser = userRepository.saveAndFlush(user);
        return profileMapper.userToUserProfileResponse(savedUser);
    }

    @Transactional
    public UserProfileResponse updateUserAddress(AddressUpdateRequest addressUpdateRequest) {
        final UserAccount userAccount = userAccountService.getCurrentUser();
        final User user = userRepository.findByAccountId(userAccount.getId())
                .orElseThrow(() -> new MappingException("Invalid username '" + userAccount.getUsername() + "'"));

        final Address address = user.getAddress();
        address.setAddressLine(addressUpdateRequest.getAddressLine());
        address.setStreet(addressUpdateRequest.getStreet());
        address.setCity(addressUpdateRequest.getCity());

        Country addressCountry = countryRepository.findByCodeIgnoreCase(addressUpdateRequest.getCountryCode())
                .orElseThrow(() -> new InvalidRequestException("Invalid countryCode '" + addressUpdateRequest.getCountryCode() + "'"));
        address.setCountry(addressCountry);

        addressRepository.saveAndFlush(address);
        return profileMapper.userToUserProfileResponse(user);
    }

    @Transactional
    public UserProfileResponse updateUserPreference(PreferenceUpdateRequest preferenceUpdateRequest) {
        final UserAccount userAccount = userAccountService.getCurrentUser();
        final User user = userRepository.findByAccountId(userAccount.getId())
                .orElseThrow(() -> new MappingException("Invalid username '" + userAccount.getUsername() + "'"));

        final String currencyCode = preferenceUpdateRequest.getCurrencyCode();
        Currency currency = currencyRepository.findByCodeIgnoreCase(currencyCode)
                .orElseThrow(() -> new InvalidRequestException("Invalid currencyCode '" + currencyCode + "'"));

        final Set<String> notificationMethods = preferenceUpdateRequest.getNotificationMethods();

        final Set<NotificationChannel> notificationChannels = notificationMethods
                .stream()
                .map(notificationMethod -> notificationChannelRepository.findByCodeIgnoreCase(notificationMethod)
                        .orElseThrow(() -> new InvalidRequestException("Invalid notificationMethod '" + notificationMethod + "'"))).collect(Collectors.toSet());

        final UserPreference userPreference = user.getPreference();
        userPreference.setCurrency(currency);
        userPreference.setNotificationChannels(notificationChannels);
        userPreferenceRepository.saveAndFlush(userPreference);

        return profileMapper.userToUserProfileResponse(user);
    }
}
