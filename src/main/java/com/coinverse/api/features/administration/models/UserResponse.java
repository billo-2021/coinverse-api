package com.coinverse.api.features.administration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AccountResponse account;
    private AddressResponse address;
    private OffsetDateTime createdAt;
    private OffsetDateTime updateAt;
}
