package com.coinverse.api.features.administration.controllers;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.administration.models.UserResponse;
import com.coinverse.api.features.administration.services.AdministrationService;
import com.coinverse.api.common.validators.PageRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdministrationController.PATH)
@RequiredArgsConstructor
public class AdministrationController {
    public static final String PATH = "/api/v1/administration";

    private final AdministrationService administrationService;
    private final PageRequestValidator pageRequestValidator;

    @GetMapping("/users")
    ResponseEntity<PageResponse<UserResponse>> getUsers(@RequestParam(required = false) Integer pageNumber,
                                                        @RequestParam(required = false) Integer pageSize) {
        PageResponse<UserResponse> usersPageResponse = administrationService
                .getUsers(pageRequestValidator.validatePageRequest(pageNumber, pageSize));

        return ResponseEntity.ok(usersPageResponse);
    }

    @PatchMapping("/users/{username}/disable-account")
    public ResponseEntity<String> disableUserAccount(@PathVariable String username) {
        administrationService.disableUserAccount(username);

        return ResponseEntity.ok("Account account disabled successfully");
    }

    @PatchMapping("/users/{username}/enable-account")
    public ResponseEntity<String> enableUserAccount(@PathVariable String username) {
        administrationService.enableUserAccount(username);

        return ResponseEntity.ok("Account account enabled successfully");
    }
}
