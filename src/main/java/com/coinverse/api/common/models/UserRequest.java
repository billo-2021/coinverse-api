package com.coinverse.api.common.models;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRequest {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AccountRequest account;
    private AddressRequest address;
    private UserPreferenceRequest preference;
}
