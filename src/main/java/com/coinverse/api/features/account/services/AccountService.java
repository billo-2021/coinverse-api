package com.coinverse.api.features.account.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserAccountEventResponse;
import com.coinverse.api.features.account.models.UpdatePasswordRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    void updatePassword(UpdatePasswordRequest updatePasswordRequest);
    PageResponse<UserAccountEventResponse> getAccountActivity(Pageable pageable);
}
