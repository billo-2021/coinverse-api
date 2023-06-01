package com.coinverse.api.common.models;

import com.coinverse.api.common.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRoleRequest {
    private String name;
}
