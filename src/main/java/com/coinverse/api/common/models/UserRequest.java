package com.coinverse.api.common.models;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String roleName;
    private String passwordHash;
    private String passwordSalt;
}
