package com.coinverse.api.features.authentication.models;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String roleName;
}
