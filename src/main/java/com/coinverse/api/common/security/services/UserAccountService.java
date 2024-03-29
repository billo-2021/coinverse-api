package com.coinverse.api.common.security.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.security.models.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserAccountService extends UserDetailsService {
    UserAccount getUserAccountById(Long id);
    UserAccount getCurrentUser();
    Account getCurrentUserAccount();
}
