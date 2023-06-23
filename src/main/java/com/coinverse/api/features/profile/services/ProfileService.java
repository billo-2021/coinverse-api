package com.coinverse.api.features.profile.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.models.UserUpdateRequest;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.profile.models.ProfileUpdateRequest;
import com.coinverse.api.features.profile.models.UpdatePasswordRequest;
import com.coinverse.api.features.profile.validators.ProfileRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final ProfileRequestValidator profileRequestValidator;

    public void updateUserProfile(ProfileUpdateRequest profileUpdateRequest) {
        final UserAccount userPrincipal = getCurrentUser();

        UserResponse userResponse = profileRequestValidator.validateUserAccountId(userPrincipal.getId());
        String userEmailAddress = userResponse.getEmailAddress();

        UserUpdateRequest userUpdateRequest = profileRequestValidator
                .validateProfileUpdate(profileUpdateRequest);
        userService.updateUserByEmailAddress(userEmailAddress, userUpdateRequest);
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        final UserAccount userPrincipal = getCurrentUser();

        final Account account = profileRequestValidator.validateChangePasswordRequest(userPrincipal.getUsername(),
                updatePasswordRequest);
        account.setPassword(passwordEncoder.encode(updatePasswordRequest.getNewPassword()));

        accountRepository.save(account);
    }

    private UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
