package com.coinverse.api.features.account.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AccountController.PATH)
@RequiredArgsConstructor
public class AccountController {
    public static final String PATH = "/api/account";
}
