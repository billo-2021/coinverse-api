package com.coinverse.api.common.security.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.security.exceptions.UserAccountNotFoundException;
import com.coinverse.api.common.security.mappers.UserAccountMapper;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.validators.UserAccountRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final AccountRepository accountRepository;
    private final UserAccountMapper userAccountMapper;
    private final UserAccountRequestValidator userAccountRequestValidator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsernameIgnoreCase(username)
                .map(userAccountMapper::accountToUserAccount)
                .orElseThrow(UserAccountNotFoundException::new);
    }

    @Override
    public UserAccount getUserAccountById(Long id) {
        return accountRepository.findById(id)
                .map(userAccountMapper::accountToUserAccount)
                .orElseThrow(UserAccountNotFoundException::new);
    }

    @Override
    public UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    @Override
    public Account getCurrentUserAccount() {
        return userAccountRequestValidator.validateUsername(getCurrentUser().getUsername());
    }
}
