package com.coinverse.api.common.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserResponse {
    @JsonIgnore
    private Long id;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private AccountResponse account;
}
