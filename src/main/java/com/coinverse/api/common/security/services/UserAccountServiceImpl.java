package com.coinverse.api.common.security.services;

import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.security.exceptions.UserAccountNotFoundException;
import com.coinverse.api.common.security.mappers.UserAccountMapper;
import com.coinverse.api.common.security.models.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final AccountRepository accountRepository;
    private final UserAccountMapper userAccountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsernameIgnoreCase(username)
                .map(userAccountMapper::accountToUserAccount)
                .orElseThrow(UserAccountNotFoundException::new);
    }

    @Override
    public Optional<UserAccount> getUserAccountById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
